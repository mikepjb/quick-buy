package org.example;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

// There appears to be a seperate way to load templates with more magic.. maybe this is more
// standard.
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@RestController
public class App {
    private final ResourceLoader resourceLoader;

    public App(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader; // TODO where does it come from?
    }

    public static void main(String[] args) {
      SpringApplication.run(App.class, args);
    }

    @GetMapping("/")
    public String index() throws Exception { // throwing Exception not ideal but I want my code to
                                            // work, I suspect this is the line of reasoning most
                                            // Java developers take in the beginning.
        String templatePath = "classpath:templates/index.html";
        Resource resource = resourceLoader.getResource(templatePath);
        return new String(resource.getInputStream().readAllBytes());
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
      return String.format("Hello %s!", name);
    }

    @GetMapping("/error")
    public String error(String name) {
      return String.format("Page not found under %s", name);
    }

    public String testFunction() {
        return "yes, this is a good function.";
    }
}
