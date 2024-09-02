package macrebot.serverproject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PalindromeRestController {

    @GetMapping("/palindrome/{word}")
    public String palindrome(@PathVariable String word) {
        int word_lenght = word.length();

        for (int i = 0; i <= word_lenght / 2; i++) {
            System.out.println("i: " + i + ". J: " + (word_lenght - i - 1));
            if (word.charAt(i) != word.charAt(word_lenght - i - 1)) {
                return word + " is NOT a Palindrome";
            }
        }

        return word + " is a Palindrome";
    }

}
