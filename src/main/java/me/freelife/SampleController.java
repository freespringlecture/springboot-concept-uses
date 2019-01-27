package me.freelife;

import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class SampleController {

    @GetMapping("/hello")
    public Resource<Hello> hello(){
        Hello hello = new Hello();
        hello.setPrefix("Hey,");
        hello.setName("freelife");

        Resource<Hello> helloResource = new Resource<>(hello);
        //SampleController 클래스에 존재하는 hello라는 메서드에 대한 링크를 만들어서 self라는 릴레이션을 만들어서 추가함
        helloResource.add(linkTo(methodOn(SampleController.class).hello()).withSelfRel());

        return helloResource;
    }
}
