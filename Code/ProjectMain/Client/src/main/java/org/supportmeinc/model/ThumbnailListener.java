package org.supportmeinc.model;

import shared.Thumbnail;

public interface ThumbnailListener {
    public void thumbnailsReceived(Thumbnail[] access, Thumbnail[] author);
}
