package com.example.recipeproject.bootstrap;

import com.example.recipeproject.domain.Category;
import com.example.recipeproject.domain.Difficulty;
import com.example.recipeproject.domain.Ingredient;
import com.example.recipeproject.domain.Notes;
import com.example.recipeproject.domain.Recipe;
import com.example.recipeproject.domain.UnitOfMeasure;
import com.example.recipeproject.repositories.CategoryRepository;
import com.example.recipeproject.repositories.RecipeRepository;
import com.example.recipeproject.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;


    public RecipeBootstrap(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recipeRepository.saveAll(getRecipes());
        log.debug("Loading Bootstrap Data");
    }

    private void validateUomOptional(Optional<UnitOfMeasure> unitOfMeasureOptional) {
        if(!unitOfMeasureOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }
    }

    private void validateCategoryOptional(Optional<Category> categoryOptional) {
        if(!categoryOptional.isPresent()) {
            throw new RuntimeException("Expected Category Not Found");
        }
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>(2);

        //get UOMs
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("each");
        validateUomOptional(eachUomOptional);

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("tablespoon");
        validateUomOptional(tableSpoonUomOptional);

        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("teaspoon");
        validateUomOptional(teaSpoonUomOptional);

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("dash");
        validateUomOptional(dashUomOptional);

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("pint");
        validateUomOptional(pintUomOptional);

        Optional<UnitOfMeasure> cupUomOptional = unitOfMeasureRepository.findByDescription("cup");
        validateUomOptional(cupUomOptional);

        //get Optionals
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teaSpoonUom = teaSpoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupUom = cupUomOptional.get();

        //get Categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
        validateCategoryOptional(americanCategoryOptional);

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");
        validateCategoryOptional(mexicanCategoryOptional);

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections(
                "Cut the avocados in half. Remove the pit. " +
                        "Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. " +
                        "Place in a bowl." +
                        "\nUsing a fork, roughly mash the avocado. " +
                        "(Don't overdo it! The guacamole should be a little chunky.)" +
                        "\nSprinkle with salt and lime (or lemon) juice. " +
                        "The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                        "\n" +
                        "Add the chopped onion, cilantro, black pepper, and chilis. " +
                        "Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat.\n" +
                        "\n" +
                        "Remember that much of this is done to taste because of the variability in the fresh ingredients. " +
                        "Start with this recipe and adjust to your taste." +
                        "If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it. " +
                        "(The oxygen in the air causes oxidation which will turn the guacamole brown.)\n" +
                        "\n" +
                        "Garnish with slices of red radish or jigama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips.\n" +
                        "\n" +
                        "Refrigerate leftover guacamole up to 3 days.\n" +
                        "\n" +
                        "Note: Chilling tomatoes hurts their flavor. So, if you want to add chopped tomato to your guacamole, add it just before serving."
        );
        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("Once you have basic guacamole down, feel free to experiment with variations by adding " +
                "strawberries, peaches, pineapple, mangoes, or even watermelon. One classic Mexican guacamole has " +
                "pomegranate seeds and chunks of peaches in it . You can get creative with your homemade guacamole!\n" +
                "\n" +
                "Simple Guacamole: The simplest version of guacamole is just mashed avocados with salt. Don't let the " +
                "lack of other ingredients stop you from making guacamole.\n" +
                "Quick guacamole: For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your " +
                "mashed avocados.\n" +
                "Don't have enough avocados? To extend a limited supply of avocados, add either sour cream or cottage " +
                "cheese to your guacamole dip. Purists may be horrified, but so what? It still tastes great.");

        guacRecipe.setNotes(guacNotes);

        guacRecipe.addIngredient(new Ingredient("ripe avocados",
                new BigDecimal(2),
                eachUom
        ));
        guacRecipe.addIngredient(new Ingredient("kosher salt",
                new BigDecimal(".5"),
                teaSpoonUom
        ));
        guacRecipe.addIngredient(new Ingredient("fresh lime or lemon juice",
                new BigDecimal(2),
                tableSpoonUom
        ));
        guacRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion",
                new BigDecimal(2),
                tableSpoonUom
        ));
        guacRecipe.addIngredient(new Ingredient("serrano (or jalapeño) chilis, stems and seeds removed, minced",
                new BigDecimal(2),
                eachUom
        ));
        guacRecipe.addIngredient(new Ingredient("cilantro (leaves and tender stems), finely chopped",
                new BigDecimal(2),
                tableSpoonUom
        ));
        guacRecipe.addIngredient(new Ingredient("freshly ground black pepper",
                new BigDecimal(2),
                dashUom
        ));
        guacRecipe.addIngredient(new Ingredient("ripe tomato, chopped (optional)",
                new BigDecimal(".5"),
                eachUom
        ));

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);

        recipes.add(guacRecipe);

        //todo: add tacos recipe

        return recipes;
    }
}
