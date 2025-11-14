package academy.devdojo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/heroes")
public class HeroesController {
    public static final List<String> HEROES = List.of("Guts", "Zoro", "Vegapunk", "Gon");

    @GetMapping
    public List<String> listAllHeroes(){
        return HEROES;
    }
    @GetMapping("filter")
    public List<String> listAllHeroesFilter(@RequestParam(defaultValue = "") String name){
        return HEROES.stream().filter(hero -> hero.equalsIgnoreCase(name)).toList();
    }
    @GetMapping("filterList")
    public List<String> listAllHeroesParamList(@RequestParam(defaultValue = "") List<String> names){
        return HEROES.stream().filter(names::contains).toList();
    }
}
