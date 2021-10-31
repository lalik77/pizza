package com.stackoverflow.pizza.controller;


import com.stackoverflow.pizza.model.Ingredient;
import com.stackoverflow.pizza.model.Pizza;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/create")
public class CreatePizzaController {

    @ModelAttribute
    public void addIngredientsToModel(Model model) {

        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("CLS22", "Classic base 22cm", Ingredient.Type.BASIC),
                new Ingredient("CLS30", "Classic base 30cm", Ingredient.Type.BASIC),
                new Ingredient("PEPE", "Pepperoni", Ingredient.Type.MEAT),
                new Ingredient("HAM", "Ham", Ingredient.Type.MEAT),
                new Ingredient("CHMPG", "Champignons", Ingredient.Type.VEGGIES),
                new Ingredient("CUCU", "Cucumbers", Ingredient.Type.VEGGIES),
                new Ingredient("PARM", "Parmesan cheese", Ingredient.Type.CHEESE),
                new Ingredient("MOZ", "Mozzarella cheese", Ingredient.Type.CHEESE),
                new Ingredient("BARBS", "Barbecue sauce", Ingredient.Type.SAUCE),
                new Ingredient("TOMAS", "Tomato sauce", Ingredient.Type.SAUCE)
        );

        Ingredient.Type [] types = Ingredient.Type.values();

        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),filterByType(ingredients,type));

        }

    }

    @GetMapping
    public String showCreateForm(Model model) {

        model.addAttribute("create",new Pizza());

        return "create";
    }


    @PostMapping
    public String processCreateForm(@Valid @ModelAttribute("create") Pizza create, Errors errors) {

        if (errors.hasErrors()) {
            log.info("Errors in processCreateForm method");
            return "create";
        }

        log.info("Processing creation : " + create);

        return "redirect:/orders/current";


    }


    private List<Ingredient> filterByType(
            List<Ingredient> ingredinets,
            Ingredient.Type type
    ) {

        return ingredinets
                .stream()
                .filter(x->x.getType().equals(type))
                .collect(Collectors.toList());
    }




}
