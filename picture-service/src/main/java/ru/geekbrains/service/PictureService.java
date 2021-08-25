package ru.geekbrains.service;

import ru.geekbrains.persist.model.Picture;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface PictureService {

    Optional<String> getPictureContentTypeById(long _id);

    List<Picture> getPicturesByProduct(long _id);

    Optional<byte[]> getPictureDataById(long _id);

    String CreatePicture(byte[] pictureStream);

    void deleteById(Long _id, String _storageId);

    List<File> getFileList(List<Picture> _picturesList);

}
