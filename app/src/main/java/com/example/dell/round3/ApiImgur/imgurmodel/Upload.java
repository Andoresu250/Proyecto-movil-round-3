package com.example.dell.round3.ApiImgur.imgurmodel;


import java.io.File;

public class Upload {
  public File image;
  public String title;
  public String description;
  public String albumId;

  public Upload(File image) {
    this.image = image;
  }
}
