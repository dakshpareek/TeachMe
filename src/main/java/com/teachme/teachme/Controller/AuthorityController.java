package com.teachme.teachme.Controller;

import com.teachme.teachme.Entity.Authority;
import com.teachme.teachme.DTO.AuthorityDTO;
import com.teachme.teachme.Entity.DAOUser;
import com.teachme.teachme.Service.AuthorityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@Validated
public class AuthorityController {

    private AuthorityService authorityService;

    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @PostMapping("/authority")
    public ResponseEntity<Authority> createAuthority(@Valid @RequestBody AuthorityDTO authorityDTO)
    {
        //System.out.println(authorityDTO.toString());
        return ResponseEntity.ok(authorityService.createAuthorityService(authorityDTO));
    }

    @DeleteMapping("/authority")
    public ResponseEntity deleteAuthority(@Valid @RequestBody AuthorityDTO authorityDTO)
    {
        authorityService.deleteAuthorityService(authorityDTO);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Authority deleted");
        body.put("status",200);
        body.put("path","/authority");
        return new ResponseEntity(body,HttpStatus.OK);
    }

    @PostMapping("/assign_authority")
    public ResponseEntity<DAOUser> setAuthority(@Valid @RequestBody AuthorityDTO authorityDTO)
    {
        return ResponseEntity.ok(authorityService.setAuthorityService(authorityDTO));
    }

    @PostMapping("/remove_authority")
    public ResponseEntity<DAOUser> removeAuthority(@Valid @RequestBody AuthorityDTO authorityDTO)
    {
        return ResponseEntity.ok(authorityService.removeAuthorityService(authorityDTO));
    }



}
