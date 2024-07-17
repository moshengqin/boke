package com.example.demo.config;

public class GetTokenId {
    public static ThreadLocal<Integer> context = new ThreadLocal<>();
    public static void setUserId(Integer Id) {
        context.set(Id);
    }
    public static Integer getUserId() {
        return context.get();
    }

    public static void shutdown() {
        context.remove();
    }
}

