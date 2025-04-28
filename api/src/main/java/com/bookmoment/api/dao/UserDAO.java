package com.bookmoment.api.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SecondaryRow;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class UserDAO {
    private String name;
}
