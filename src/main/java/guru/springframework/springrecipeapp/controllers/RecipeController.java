package guru.springframework.springrecipeapp.controllers;

import guru.springframework.springrecipeapp.commands.RecipeCommand;
import guru.springframework.springrecipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RecipeController {

    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping("/recipe/{id}/show")
    public String getRecipeById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", service.getById(Long.parseLong(id)).get());

        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/form";
    }

    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", service.getCommandById(Long.parseLong(id)).get());

        return "recipe/form";
    }

    @PostMapping("/recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand savedRecipeCommand = service.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id, Model model) {
        service.deleteById(Long.parseLong(id));

        return "redirect:/";
    }

}
