# API - Pior Filme do Golden Raspberry Awards.

Projeto backend em java com springboot

## Sobre o projeto

* O projeto utiliza o banco de dados em memória H2
* O projeto também utiliza FlyWay para versionamento do banco de dados
* O projeto roda na porta padrão 8080

### Objetivo do projeto
O projeto tem por objetivo efetuar a leitura da lista de indicados e vencedores
da categoria Pior Filme do Golden Raspberry Awards, salvar esses dados em banco de dados e expor um endpoint para
obter o produtor com maior intervalo entre dois prêmios consecutivos, e o que obteve dois prêmios mais rápido.

### Endpoints

Verbo `GET` 
- http://localhost:8000/v1/movies

### Exemplo de resposta:
```json
{
	"min": [{
		"producer": "Joel Silver",
		"interval": 1,
		"previousWin": 1990,
		"followingWin": 1991
	}],
	"max": [{
		"producer": "Matthew Vaughn",
		"interval": 13,
		"previousWin": 2002,
		"followingWin": 2015
	}]
}
```

### Built With
- IntelliJ IDEA
- Java 17 - Execution platform.
- Spring Boot - 2.7.5.
- H2 Database.
- Project Lombok.
- JUnit.
- Maven.
- Docker
- Docker Compose

### Rodando o projeto
Para rodar localmente execute os passos a seguir.

Pré requisitos
Esse projeto requer java 17 ou superior e Maven compatível.


Clone o repositório:

- COM HTTPS ->  git clone https://github.com/MarcosDanielBudtinger/gra.git
- COM SSH -> git clone git@github.com:MarcosDanielBudtinger/gra.git

Temos algumas formas de rodar o projeto sendo elas: 
- Abrir o projeto na IDE e rodar a classe GraApplication.
- Abrir o terminal na pasta do projeto executar o comando mvn clean install e depois do build rodar o jar como nome spring-boot-docker que fica dentro da pasta target ex: `java -jar target/spring-boot-docker.jar`
- Rodar via docker, na raiz do projeto vc pode rodar o comando `docker build .`
na saida teremos algo como na imagem a seguir: 
![image](https://user-images.githubusercontent.com/19701042/201356547-242de0f2-a9a4-4ea5-8db2-062ffe8e4131.png)

repare que temos o nome do build como 2f259f03b214, feito isso basta executar o comando `docker run -p 8080:8080 2f259f03b214`
Pronto a aplicação irá subir.

Efetua a chamada a seguir:
http://localhost:8000/v1/movies

Para executar os testes basta rodar os mesmos nas classes MovieResourceTest para testes do resource e MovieServiceTest
