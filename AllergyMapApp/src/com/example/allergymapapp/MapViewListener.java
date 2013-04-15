package com.example.allergymapapp;

import com.google.android.maps.GeoPoint;

public interface MapViewListener {
    void onPan(GeoPoint oldTopLeft,
               GeoPoint oldCenter,
               GeoPoint oldBottomRight,
               GeoPoint newTopLeft,
               GeoPoint newCenter,
               GeoPoint newBottomRight);
    void onZoom(GeoPoint oldTopLeft,
                GeoPoint oldCenter,
                GeoPoint oldBottomRight,
                GeoPoint newTopLeft,
                GeoPoint newCenter,
                GeoPoint newBottomRight,
                int oldZoomLevel,
                int newZoomLevel);
    void onClick(GeoPoint clickedPoint);
}