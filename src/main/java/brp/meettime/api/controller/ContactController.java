package brp.meettime.api.controller;

import brp.meettime.api.model.ContactDTO;
import brp.meettime.api.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createContact(@RequestBody ContactDTO contactDTO, @RequestHeader("Authorization") String accessToken) {
        return ResponseEntity.ok(contactService.createContact(contactDTO, accessToken));
    }
}
