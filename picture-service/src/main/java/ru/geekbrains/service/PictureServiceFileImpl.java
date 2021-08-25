package ru.geekbrains.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.PictureRepository;
import ru.geekbrains.persist.model.Picture;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PictureServiceFileImpl implements PictureService {

    private static final Logger logger = LoggerFactory.getLogger(PictureServiceFileImpl.class);

    @Value("${picture.storage.path}")
    private String storagePath;

    @Autowired
    private final PictureRepository pictureRepository;

    public PictureServiceFileImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Optional<String> getPictureContentTypeById(long _id) {
        return pictureRepository.findById(_id).map(Picture::getContentType);
    }

    @Override
    public List<Picture> getPicturesByProduct(long _id) {
        return pictureRepository.findAllByProductId(_id);
    }

    @Override
    public Optional<byte[]> getPictureDataById(long _id) {
        return pictureRepository.findById(_id)
                .map(pic -> Paths.get(storagePath, pic.getStorageId()))
                .filter(Files::exists)
                .map(path -> {
                    try {
                        return Files.readAllBytes(path);
                    } catch (IOException e) {
                        logger.error("Can't read file for picture with id " + _id, e);
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public String CreatePicture(byte[] pictureStream) {
        String fileName = UUID.randomUUID().toString();
        try(OutputStream os = Files.newOutputStream(Paths.get(storagePath, fileName))){
            os.write(pictureStream);

        } catch (IOException e) {
            logger.error("Can't write file", e);
            throw new RuntimeException(e);
        }
        return fileName;
    }

    @Override
    public void deleteById(Long _id, String _storageId) {
        File file = Paths.get(storagePath, _storageId).toFile();
        if (file.exists()) {
            file.delete();
        }
        pictureRepository.deleteById(_id);
    }

    public List<File> getFileList(List<Picture> _picturesList) {
        return _picturesList.stream().map(pic -> Paths.get(storagePath, pic.getStorageId()).toFile()).filter(File::exists).collect(Collectors.toList());
    }

}
