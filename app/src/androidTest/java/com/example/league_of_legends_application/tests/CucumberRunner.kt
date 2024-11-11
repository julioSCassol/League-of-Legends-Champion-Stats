package com.example.league_of_legends_application.tests

import cucumber.api.junit.Cucumber
import cucumber.api.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/androidTest/assets"],  // Caminho para os arquivos .feature
    glue = ["com.example.league_of_legends_application.tests"]  // Caminho para o pacote com as definições de passos
)
class CucumberRunner
