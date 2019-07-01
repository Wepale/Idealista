package com.example.idealista;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IdealistaApplication {

	private final String CHALET = "CHALET";
	private final String FLAT = "FLAT";
	private final String GARAGE = "GARAGE";

	public static void main(String[] args) {
		SpringApplication.run(IdealistaApplication.class, args);
	}

	@SuppressWarnings("Duplicates")
	@Bean
	public CommandLineRunner initData(AnuncioRepository anuncioRepo, FotoRepository fotoRepo) {
		return (args) -> {

			//	Anuncios
			Anuncio anuncio1 = new Anuncio("Este piso es una ganga, compra, compra, COMPRA!!!!!", CHALET, 300, null);
			Anuncio anuncio2 = new Anuncio("Nuevo ático céntrico recién reformado. No deje pasarla oportunidad y adquiera este ático de lujo", FLAT);
			Anuncio anuncio3 = new Anuncio("", CHALET, 210, 25);
			Anuncio anuncio4 = new Anuncio("Ático céntrico muy luminoso y recién reformado, parece nuevo", FLAT, 130, null);
			Anuncio anuncio5 = new Anuncio("Pisazo", FLAT);
			Anuncio anuncio6 = new Anuncio("", GARAGE);
			Anuncio anuncio7 = new Anuncio("Garaje en el centro de Albacete", GARAGE);
			Anuncio anuncio8 = new Anuncio("Maravilloso chalet situado en als afueras de un pequeño pueblo rural. El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!", CHALET, 150, 20);


			//	Fotos
			Foto foto1 = new Foto("http://www.idealista.com/pictures/1", "SD");
			Foto foto2 = new Foto("http://www.idealista.com/pictures/2", "HD");
			Foto foto3 = new Foto("http://www.idealista.com/pictures/3", "SD");
			Foto foto4 = new Foto("http://www.idealista.com/pictures/4", "HD");
			Foto foto5 = new Foto("http://www.idealista.com/pictures/5", "SD");
			Foto foto6 = new Foto("http://www.idealista.com/pictures/6", "SD");
			Foto foto7 = new Foto("http://www.idealista.com/pictures/7", "SD");

			anuncio2.addFoto(foto4);
			anuncio3.addFoto(foto2);
			anuncio4.addFoto(foto5);
			anuncio5.addFoto(foto3);
			anuncio5.addFoto(foto4);
			anuncio6.addFoto(foto6);
			anuncio8.addFoto(foto1);

			anuncio1.setScore(anuncio1.calculateScore());
			anuncio2.setScore(anuncio2.calculateScore());
			anuncio3.setScore(anuncio3.calculateScore());
			anuncio4.setScore(anuncio4.calculateScore());
			anuncio5.setScore(anuncio5.calculateScore());
			anuncio6.setScore(anuncio6.calculateScore());
			anuncio7.setScore(anuncio7.calculateScore());
			anuncio8.setScore(anuncio8.calculateScore());

			anuncioRepo.save(anuncio1);
			anuncioRepo.save(anuncio2);
			anuncioRepo.save(anuncio3);
			anuncioRepo.save(anuncio4);
			anuncioRepo.save(anuncio5);
			anuncioRepo.save(anuncio6);
			anuncioRepo.save(anuncio7);
			anuncioRepo.save(anuncio8);

			fotoRepo.save(foto1);
			fotoRepo.save(foto2);
			fotoRepo.save(foto3);
			fotoRepo.save(foto4);
			fotoRepo.save(foto5);
			fotoRepo.save(foto6);
			fotoRepo.save(foto7);

		};
	}

}
