package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredeintController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    private final UnitOfMeasureService unitOfMeasureService;


    public IngredeintController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }
    @GetMapping
    @RequestMapping({"/recipe/{id}/ingredients"})
    public String listIngredeints( @PathVariable String id,Model model){
        log.debug("Getting ingredients from the recipe id : "+id);
        model.addAttribute("recipe",recipeService.findCommandById(new Long(id)));
        return "recipe/ingredient/list";

    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/new")
    public String newRecipe(@PathVariable String recipeId,Model model){
        RecipeCommand recipeCommand = recipeService.findCommandById(new Long(recipeId));
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(new Long(recipeId));
        model.addAttribute("ingredient",ingredientCommand);
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        model.addAttribute("uomList",unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/form";
    }
    @GetMapping
    @RequestMapping({"/recipe/{recipeId}/ingredient/{IngId}/show"})
    public String showRecipeIngredeint(@PathVariable String recipeId,@PathVariable String IngId,Model model){
        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndId(new Long(recipeId),new Long(IngId)));
        return "recipe/ingredient/show";
    }

    @PutMapping
    @RequestMapping({"recipe/{recipeId}/ingredient/{IngId}/update"})
    public String updateRecipeIngredient(@PathVariable String recipeId,@PathVariable String IngId,Model model){
        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndId(new Long(recipeId),new Long(IngId)));
        model.addAttribute("uomList",unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/form";
    }

    @PostMapping
    @RequestMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(IngredientCommand ingredientCommand){
        IngredientCommand savedCommad = ingredientService.saveIngredientCommand(ingredientCommand);
        log.debug("saved recipe id : " +savedCommad.getRecipeId());
        log.debug("saved ingredeint id " +savedCommad.getId());
        return "redirect:/recipe/"+savedCommad.getRecipeId()+"/ingredient/"+ savedCommad.getId()+"/show";
    }

    @DeleteMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId,@PathVariable String id){
        log.debug("delteing ingredirnt of id :" + id);
        ingredientService.deleteById(new Long(recipeId),new Long(id));
        return "redirect:/recipe/"+recipeId+"/ingredients";
    }

}
