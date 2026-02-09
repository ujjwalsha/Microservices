package com.ecommerce.user.Service;

import com.ecommerce.user.DTO.AddressDto;
import com.ecommerce.user.DTO.UserRequest;
import com.ecommerce.user.DTO.UserResponse;
import com.ecommerce.user.Models.Address;
import com.ecommerce.user.Models.User;
import com.ecommerce.user.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }


    public List<UserResponse> getAllusers() {

        List<User> userList = userRepository.findAll();

        return userRepository.findAll()
                .stream().map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void addUser(UserRequest userRequest) {

        if(userRequest == null)
            throw new RuntimeException("not found");

        User user = new User();

        updateUserFromRequest(user, userRequest);

        userRepository.save(user);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if(userRequest.getAddress() != null)
        {
            Address address = new Address();
            address.setCity(userRequest.getAddress().getCity());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setState(userRequest.getAddress().getState());
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCountry(userRequest.getAddress().getCountry());

            user.setAddress(address);
        }
    }

    public boolean updateUser(Long id, UserRequest userRequest) {

        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, userRequest);
                    userRepository.save(existingUser);
                    return true;
                })
                .orElse(false);
    }

    private UserResponse mapToUserResponse(User user)
    {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setPhone(user.getPhone());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());

        if(user.getAddress() != null)
        {
            AddressDto addressDto = new AddressDto();
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setState(user.getAddress().getState());
            addressDto.setZipcode(user.getAddress().getZipcode());
            addressDto.setCountry(user.getAddress().getCountry());

            userResponse.setAddress(addressDto);
        }
        
        return userResponse;
    }

    public Optional<UserResponse> fetchUser(Long id) {

        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }
}
