package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.service.PictureService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/picture")
public class PictureController {

    private final PictureService pictureService;

    private static final Logger logger = LoggerFactory.getLogger(PictureController.class);

    @Autowired
    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @GetMapping("/{pictureId}")
    public void downloadProductPicture(@PathVariable("pictureId") Long _pictureId
            , HttpServletResponse _resp) throws IOException {

        Optional<String> opt = pictureService.getPictureContentTypeById(_pictureId);
        if (opt.isPresent()){
            _resp.setContentType(opt.get());
            _resp.getOutputStream().write(pictureService.getPictureDataById(_pictureId).get());
        } else {
            _resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @DeleteMapping("/del*")
    public String productRemove(@RequestParam Map<String, String> paramList){
        logger.info(String.format("Going to delete picture with id - %s, storage - %s, related to productId - %s",
                paramList.get("id"), paramList.get("storageId"), paramList.get("productId")));

        pictureService.deleteById(Long.parseLong(paramList.get("id")), paramList.get("storageId"));

        return "redirect:/product/" + paramList.get("productId");
    }

}
