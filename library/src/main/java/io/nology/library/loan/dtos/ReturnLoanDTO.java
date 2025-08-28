package io.nology.library.loan.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ReturnLoanDTO {

    @NotNull
    @Min(1)
    Long bookId;

    public ReturnLoanDTO() {
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

}
