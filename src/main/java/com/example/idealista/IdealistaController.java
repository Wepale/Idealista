package com.example.idealista;

import javafx.beans.value.ObservableValueBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class IdealistaController {

    @Autowired
    AnuncioRepository anuncioRepo;

    @Autowired
    FotoRepository fotoRepo;


    @RequestMapping("/puntuacion")
    public List<Map<String,Object>> calcularPuntuacion(){
         return anuncioRepo.findAll()
                 .stream()
                 .sorted(Comparator.comparingInt(Anuncio::getScore).reversed())
                 .map(anuncio -> new LinkedHashMap<String, Object>(){{
                     put("id", anuncio.getId());
                     put("description", anuncio.getDescription());
                     put("tieneComentario", anuncio.tieneDescripcion());
                     put("comentarioLargo", anuncio.longuitudDescripcion());
                     put("palabrasClave", anuncio.palabrasClave());
                     put("punuacion_fotos", anuncio.puntuacionFotos());
                     put("anuncioCompleto", anuncio.anuncioCompleto());
                     put("puntuacionFinal", anuncio.getScore());
                 }})
                 .collect(toList());
    }

    @RequestMapping("/cliente")
    public List<Map<String,Object>> casasParaClientes(){
        return anuncioRepo.findAll()
                .stream()
                .filter(anuncio -> anuncio.getScore() >=40)
                .sorted(Comparator.comparingInt(Anuncio::getScore).reversed())
                .map(Anuncio::anuncioClienteDTO)
                .collect(toList());
    }

    @RequestMapping("calidad")
    public List<Map<String, Object>> casasParaCalidad() {
        return anuncioRepo.findAll()
                .stream()
                .filter(anuncio -> anuncio.getScore() <=40)
                .sorted(Comparator.comparingInt(Anuncio::getScore).reversed())
                .map(Anuncio::anuncioCalidadDTO)
                .collect(toList());
    }

}
