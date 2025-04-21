package com.example.miniBank

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MiniBankApplication

fun main(args: Array<String>) {
	runApplication<MiniBankApplication>(*args)
}
