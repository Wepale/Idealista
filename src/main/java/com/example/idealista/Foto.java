package com.example.idealista;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String url;
    private String quality;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="anuncio_id")
    private Anuncio anuncio;

    public Foto(){}

    public Foto(String url, String quality){
        this.url = url;
        this.quality = quality;
    }

    public void setAnuncio(Anuncio anuncio){
        this.anuncio = anuncio;
    }

    public String getQuality(){
        return quality;
    }

    public Long getPuntuacion() {
        return quality.equals("HD")
                ? 20L
                : 10L;
    }

    public Map<String, Object> fotoDTO(){
        return new LinkedHashMap<String, Object>(){{
            put("id", id);
            put("url", url);
            put("quality", quality);
        }};
    }
}
