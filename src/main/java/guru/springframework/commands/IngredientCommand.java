package guru.springframework.commands;

import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.cfg.BinderHelper;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private String description;
    private UnitOfMeasureCommand unitOfMeasureCommand;
    private BigDecimal amount;
    private Recipe recipe;
}
