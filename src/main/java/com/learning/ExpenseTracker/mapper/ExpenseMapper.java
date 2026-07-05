package com.learning.ExpenseTracker.mapper;

import com.learning.ExpenseTracker.dto.ExpenseDTO;
import com.learning.ExpenseTracker.model.Expense;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    // Entity -> DTO
    ExpenseDTO toDTO(Expense expense);

    // DTO -> Entity
    Expense toEntity(ExpenseDTO expenseDTO);

    // List<Entity> -> List<DTO>
    List<ExpenseDTO> toDTOList(List<Expense> expenses);

    // List<DTO> -> List<Entity>
    List<Expense> toEntityList(List<ExpenseDTO> expenseDTOList);
}