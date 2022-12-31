package com.inflearn.demo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Integer id;
    private String description;

    // User : Post -> 1 : (0~N) Main : Sub -> Parent : Child
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Users user;
}
