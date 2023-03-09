package guru.springframework.springrecipeapp.controllers;

import guru.springframework.springrecipeapp.commands.RecipeCommand;
import guru.springframework.springrecipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {

    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @RequestMapping("/recipe/show/{id}")
    public String getRecipeById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", service.getById(Long.parseLong(id)).get());

        return "recipe/show";
    }

    @RequestMapping("/recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/form";
    }

    @PostMapping("/recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand savedRecipeCommand = service.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/show/" + savedRecipeCommand.getId();
    }
}
