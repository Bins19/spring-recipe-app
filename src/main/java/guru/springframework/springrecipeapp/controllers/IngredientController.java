package guru.springframework.springrecipeapp.controllers;

import guru.springframework.springrecipeapp.commands.IngredientCommand;
import guru.springframework.springrecipeapp.commands.RecipeCommand;
import guru.springframework.springrecipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.springrecipeapp.services.IngredientService;
import guru.springframework.springrecipeapp.services.RecipeService;
import guru.springframework.springrecipeapp.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class IngredientController {

    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final UnitOfMeasureService uomService;

    public IngredientController(IngredientService ingredientService, RecipeService recipeService,
                                UnitOfMeasureService uomService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.uomService = uomService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String getIngredients(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.getCommandById(Long.parseLong(recipeId)).get());

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
        model.addAttribute("ingredient",
                ingredientService.getByIdAndRecipeId(Long.parseLong(id), Long.parseLong(recipeId)).get());

        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String createIngredient(@PathVariable String recipeId, Model model) {
        Optional<RecipeCommand> recipeCommand = recipeService.getCommandById(Long.parseLong(recipeId));

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.parseLong(recipeId));
        model.addAttribute("ingredient", ingredientCommand);
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("uomList", uomService.getAll());

        return "recipe/ingredient/form";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
        model.addAttribute("ingredient", ingredientService.getByIdAndRecipeId(Long.parseLong(id),
                                                                                          Long.parseLong(recipeId))
                                                                       .get());
        model.addAttribute("uomList", uomService.getAll());

        return "recipe/ingredient/form";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand savedCommand = ingredientService.saveCommand(ingredientCommand);

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String delete(@PathVariable String recipeId, @PathVariable String id, Model model) {
        ingredientService.delete(Long.parseLong(id), Long.parseLong(recipeId));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

}
