package com.example.idealista;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
public class Anuncio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String date;
    private String description;
    private String typology;
    private Integer houseSize;
    private Integer gardenSize;
    private Integer score;

    @OneToMany(mappedBy = "anuncio", fetch= FetchType.EAGER)
    private List<Foto> fotos = new LinkedList<>();

    public Anuncio(){}

    public Anuncio(String description, String typology) {
        this.description = description;
        this.typology = typology;
        this.date = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now());
    }

    public Anuncio(String description, String typology, Integer houseSize, Integer gardenSize) {
        this.description = description;
        this.typology = typology;
        this.houseSize = houseSize;
        this.gardenSize = gardenSize;
        this.date = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now());
    }

    public void addFoto(Foto foto){
        fotos.add(foto);
        foto.setAnuncio(this);
    }

    public Long getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }

    public Integer getHouseSize() {
        return houseSize;
    }

    public String getTypology() {
        return typology;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getGardenSize() {
        return gardenSize;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer tieneDescripcion(){
        return description.isEmpty()
                ? 0
                : 5;
    }

    public Integer longuitudDescripcion() {
        switch (typology) {
            case "FLAT":
                if (description.split(" ").length < 20) {
                    return 0;
                } else if (description.split(" ").length >= 50){
                    return 30;
                } else {
                    return 20;
                }
            case "CHALET":
                return description.split(" ").length >= 50
                        ? 20
                        : 0;
            default:
                return 0;
        }

    }

    public Integer palabrasClave() {
        return (int)Arrays.stream(description.split(" ")).map(String::toLowerCase)
                .filter(palabra -> palabra.equals("luminoso")
                        || palabra.equals("nuevo")
                        || palabra.equals("céntrico")
                        || palabra.equals("reformado")
                        || palabra.equals("ático"))
                .count()*5;
    }

    public Integer puntuacionFotos() {
        return fotos.isEmpty()
                ?  -10
                : fotos.stream().map(Foto::getPuntuacion).reduce((a,b) -> a + b).orElse(0L).intValue();
    }

    public Integer anuncioCompleto() {
        switch (typology) {
            case "FLAT":
               return !description.isEmpty() && !fotos.isEmpty() && houseSize != null
                       ? 40
                       : 0;
            case "CHALET":
                return !description.isEmpty() && !fotos.isEmpty() && houseSize != null && gardenSize != null
                        ? 40
                        : 0;
            default:
                return !fotos.isEmpty()
                        ? 40
                        : 0;
        }
    }

    public Integer calculateScore() {
        Integer finalScore = tieneDescripcion() + longuitudDescripcion() + palabrasClave() + puntuacionFotos() + anuncioCompleto();

        return finalScore > 100
                ? 100
                : finalScore;
    }

    public Map<String, Object> anuncioCalidadDTO() {
       return new LinkedHashMap<String, Object>() {{
           put("id", id);
           put("puntuacionFinal", score);
           put("date", date);
           put("tieneComentario", tieneDescripcion());
           put("comentarioLargo", longuitudDescripcion());
           put("palabrasClave", palabrasClave());
           put("punuacion_fotos", puntuacionFotos());
           put("anuncioCompleto", anuncioCompleto());
        }};
    }

    public Map<String, Object> anuncioClienteDTO() {
        return new LinkedHashMap<String, Object>() {{
            put("id", id);
            put("description", description);
            put("typology", typology);
            put("houseSize", houseSize);
            put("gardenSize", gardenSize);
            put("pictures", fotos.stream().map(Foto::fotoDTO));
        }};
    }

}
