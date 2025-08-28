package io.nology.library.loan.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateLoanDTO {

    @NotNull
    @Min(1)
    Long bookId;

    @NotNull
    @Min(1)
    Long memberId;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

}
