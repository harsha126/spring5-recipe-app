package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;

public interface IngredientService{
    IngredientCommand findByRecipeIdAndId(Long recipeId, Long Id);
    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteById(Long recipeId,Long Id);
}
