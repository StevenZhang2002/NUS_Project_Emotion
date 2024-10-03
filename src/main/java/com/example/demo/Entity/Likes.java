package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Likes {
    private int likeId;
    private int postId;
    private int userId;
    private boolean likeOrNot;

    public Likes(int postId, int userId, boolean b) {
        this.postId = postId;
        this.userId = userId;
        this.likeOrNot = b;
    }
}
