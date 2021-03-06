package com.example.excelProj.Service;

import com.example.excelProj.Commons.ApiResponse;
import com.example.excelProj.Dto.UserDto;
import com.example.excelProj.Model.Friend;
import com.example.excelProj.Model.User;
import com.example.excelProj.Repository.FriendRepository;
import com.example.excelProj.Repository.UserDaoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service("userDetailsService")
public class UserServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserDaoRepository userDaoRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	FriendRepository friendRepository;

	@Autowired
	JavaMailSender javaMailSender;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDaoRepository.findByEmail(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user.getUserType()));
	}

	private List<SimpleGrantedAuthority> getAuthority(String role) {
		return Arrays.asList(new SimpleGrantedAuthority(role));
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userDaoRepository.findByActive().iterator().forEachRemaining(list::add);
		return list;
	}

	public ApiResponse<List<User>> delete(Long id) {
		Optional<User> userOptional = userDaoRepository.findById(id);
		if(userOptional.isPresent()){
			userDaoRepository.deleteById(id);
		}
		return new ApiResponse<>(HttpStatus.OK.value(), "User saved successfully.",	userDaoRepository.findAll());
	}

	public User findOne(String username) {

		 User user = userDaoRepository.findByEmailAndActive(username,Boolean.TRUE);
		 return user;

	}

	public User findById(Long id) {
		Optional<User> optionalUser = userDaoRepository.findById(id);

		return optionalUser.isPresent() ?  optionalUser.get() : null;
	}

    public UserDto update(UserDto userDto, Long id) {
        User user = findById(id);
        if(user != null) {
            BeanUtils.copyProperties(userDto, user, "password");
            userDaoRepository.save(user);
        }
        return userDto;
    }

    public ApiResponse save(UserDto user) {
		User founduser = userDaoRepository.findByEmail(user.getEmail());
		if(founduser == null) {
			User newUser = new User();
			newUser.setEmail(user.getEmail());
			newUser.setName(user.getName());
			newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			newUser.setUserType(user.getUserType());
			newUser.setActive(user.getActive());
			newUser.setNoOfFriends(0);
			newUser.setNumberOfFriendRequests(0);
			newUser.setNumberOfNotifications(0);
			try {
				mailToUser(user.getEmail());
				return new ApiResponse<>(HttpStatus.OK.value(), "User saved successfully.",	userDaoRepository.save(newUser));
			}catch (Exception e){
				System.out.println(e);
				return new ApiResponse<>(HttpStatus.OK.value(), "User saved successfully but email not sent.",	userDaoRepository.save(newUser));
			}
			//return ;
		}else{
			return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "User Already exsist.",null);//return ;
		}
    }


	public List<User> getActiveUsers(Long id){
		Optional<User> user = userDaoRepository.findById(id);
		if(user.isPresent()){
			User user1 = user.get();
			user1.setActive(false);
			userDaoRepository.save(user1);

			List<User> activeUsers = userDaoRepository.findByActive();
			return activeUsers;
 		}

		return null;
	}

	public ApiResponse getUserByPatterns(String name){
			List<User> users = userDaoRepository.getUsersByPattern(name,getIdOfLoggedInUser());

			if(!users.isEmpty()){
				return new ApiResponse<>(200,"Users found",users);
			}
			else {
				return new ApiResponse<>(404,"Users not found",null);
			}

	}

	public ApiResponse findById2(Long id) {
		Optional<User> optionalUser = userDaoRepository.findById(id);
		if(optionalUser.isPresent()){
			User user = optionalUser.get();
			return new ApiResponse<>(200,getStatusOfUser(id),user);
		}
		else {
			return new ApiResponse<>(404,"User not found",null);
		}
	}

	public String getStatusOfUser(Long friendId){
		Long userId = getIdOfLoggedInUser();
		Friend friend = friendRepository.findByUserAndFriend(friendId,userId);
		Friend friend1 = friendRepository.findByUserAndFriend(userId,friendId);

		if(friend == null && friend1 != null){
			return getStatus(friend1,userId);
		}
		else if(friend != null && friend1 == null){
			return  getStatus(friend,userId);
		}
		else if(friend !=null && friend1!=null){
			return getStatus(friend,userId);
		}
		else {
			return "not friends";
		}

	}

	public String getStatus(Friend friend, Long userId){
		if((userId == friend.getUserObj().getId() && friend.getStatus().equals("pending"))){
			return "pending";
		}
		else if(userId == friend.getFriend().getId() && friend.getStatus().equals("pending")){
			return "pendingN";
		}
		else{
			return friend.getStatus();
		}
	}

	public Long getIdOfLoggedInUser()
	{
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username = userDetails.getUsername();
		User user = userDaoRepository.findByEmail(username);
		return user.getId();
	}

	public ApiResponse saveProfilePicture(UserDto userDto){
		Optional <User> user = userDaoRepository.findById(getIdOfLoggedInUser());

		if (user.isPresent()){
			User user1 = user.get();
			user1.setProfilePicture(userDto.getProfilePicture());
			userDaoRepository.save(user1);
			return new ApiResponse(200,"Profile picture updated",user1);
		}
		else{
			return new ApiResponse(400,"Error updating profile picture",null);
		}
	}


	public ApiResponse saveProfileDescription(UserDto userDto){
		Optional <User> user = userDaoRepository.findById(getIdOfLoggedInUser());


		if (user.isPresent()){
			User user1 = user.get();
			user1.setDescription(userDto.getDescription());
			return new ApiResponse(200,"User Description updated",userDaoRepository.save(user1));
		}
		else{
			return new ApiResponse(400,"Error updating user Description",null);
		}
	}


	public ApiResponse setUserFirebaseToken(Long id,UserDto userDto){
		Optional<User> user = userDaoRepository.findById(id);
		if(user.isPresent()){
			User user1 = user.get();
			user1.setFirebaseToken(userDto.getFirebaseToken());
			return new ApiResponse(200,"Firebase Token set",userDaoRepository.save(user1));
		}
		return new ApiResponse(400,"User not found",null);
 	}

 	public ApiResponse updateNoOfNotifications(Long id,UserDto userDto){
		Optional<User> user = userDaoRepository.findById(id);
		if(user.isPresent()) {
			User user1 = user.get();
			user1.setNumberOfNotifications(userDto.getNumberOfNotifications());
			user1.setNumberOfFriendRequests(userDto.getNumberOfFriendRequests());
			return new ApiResponse(200,"number of notifications updated",userDaoRepository.save(user1));
		}
		return new ApiResponse(400,"User not found",null);
	}


	void mailToUser(String recevierEmail) {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(recevierEmail);

		msg.setSubject("Confirm your email to get started");
		msg.setText("Thank you for your registration !" + "\n" +
		"We have sent you a confirmation email." + "\n" +
				"Click on the link (box) in the email to access your account." + "\n" +
				"Haven't received the email?" + "\n" +
				"Check your spam folder or click here to resend the confirmation email"+"\n"+
				" Email pour communiquer mtlsauvage@gmail.ca");

		javaMailSender.send(msg);

	}


}
