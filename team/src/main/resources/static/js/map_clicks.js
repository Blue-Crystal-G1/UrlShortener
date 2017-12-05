var popupShowing = 0;
var countryNamesToShow = [];
var countriesInfo;
var map;
var highlightStyle;

function showMapClicksGraphic(info) {
    countriesInfo = info;
    countryNamesToShow = countriesInfo.map(function (obj) {
        return obj.country;
    });

    var countries = new ol.layer.Vector({
        source: new ol.source.Vector({
            format: new ol.format.GeoJSON(),
            url: 'https://openlayers.org/en/v4.5.0/examples/data/geojson/countries.geojson'
        }),
        style: function(feature, res){
            if(mustShowClickInfo(feature.get("name"))) {
                return new ol.style.Style({
                    fill: new ol.style.Fill({
                        color: 'rgba(66,134,244,1)',
                    }),
                });
            }
            else {
                return new ol.style.Style({
                    fill: new ol.style.Fill({
                        color: 'rgba(245, 245, 245,1)'
                    }),
                    stroke: new ol.style.Stroke({
                        color: 'rgba(215, 215, 215,1)',
                        width: 0.3
                    })
                });
            }
        }
    });

    var view = new ol.View({
        center: ol.proj.transform([0,45], 'EPSG:4326', 'EPSG:3857'),
        zoom: 1,
        minZoom: 1,
        maxZoom: 1,
        zoomDuration: 1,
        controls: []
    });

    /**
     * Elements that make up the popup.
     */
    var container = document.getElementById('popup');
    var content = document.getElementById('popup-content');

    /**
     * Create an overlay to anchor the popup to the map.
     */
    var overlay = new ol.Overlay(/** @type {olx.OverlayOptions} */ ({
        element: container,
        autoPan: true,
        autoPanAnimation: {
            duration: 250
        }
    }));

    map = new ol.Map({
        target: 'map',
        layers: [countries],
        view: view,
        overlays: [overlay],
        controls: [],
        interactions: []
    });

    // when we move the mouse over a feature, we can change its style to
    // highlight it temporarily
    highlightStyle = new ol.style.Style({
        fill: new ol.style.Fill({
            color: 'rgba(66,134,244,1)',
        }),
        stroke: new ol.style.Stroke({
            color: '#000000',
            width: 1
        }),
        zIndex: 1
    });

    var collection = new ol.Collection();
    var featureOverlay = new ol.layer.Vector({
        map: map,
        style: styleFunction,
        source: new ol.source.Vector({
            features: collection
        })
    });


    // when the mouse moves over the map, we get an event that we can use
    // to create a new feature overlay from
    map.on('pointermove', function(browserEvent) {
        // first clear any existing features in the overlay
        featureOverlay.getSource().clear();
        var coordinate = browserEvent.coordinate;
        var pixel = browserEvent.pixel;
        // then for each feature at the mouse position ...
        map.forEachFeatureAtPixel(pixel, function(feature, layer) {
            // check the layer property, if it is not set then it means we
            // are over an OverlayFeature and we can ignore this feature
            // It also checks if a country needs to show clicks. If not, ommit it
            // If the popup with click information is showed, close it too
            if (!layer || !mustShowClickInfo(feature.get('name'))) {
                overlay.setPosition(undefined);
                popupShowing = 0;
                return;
            }

            // test the feature's geometry type and compute a reasonable point
            // at which to display the text.
            var geometry = feature.getGeometry();
            var point;
            switch (geometry.getType()) {
                case 'MultiPolygon':
                    var poly = geometry.getPolygons().reduce(function(left, right) {
                        return left.getArea() > right.getArea() ? left : right;
                    });
                    point = poly.getInteriorPoint().getCoordinates();
                    break;
                case 'Polygon':
                    point = geometry.getInteriorPoint().getCoordinates();
                    break;
                default:
                    point = geometry.getClosestPoint(coordinate);
            }

            if(popupShowing === 0) {
                popupShowing = 1;
                content.innerHTML = '<p><strong>' + feature.get('name') + '</strong><br>Clicks: <strong>'
                    + getClicksByCountry(feature.get('name')) + '</strong></p>';
                overlay.setPosition(point);
            }

            // and add it to the featureOverlay.  Also add the feature itself
            // so the country gets outlined
            //featureOverlay.getSource().addFeature(textFeature);
            featureOverlay.getSource().addFeature(feature);
        });
    });
}

function getClicksByCountry(str) {
    var result = $.grep(countriesInfo, function(e){ return e.country === str; });
    return (result.length === 1) ? result[0].counter : 0;
}

function mustShowClickInfo(str) {
    return countryNamesToShow.indexOf(str) > -1;
}

// the style function for the feature overlay returns
// a text style for point features and the highlight
// style for other features (polygons in this case)
function styleFunction(feature, resolution) {
    var style;
    var geom = feature.getGeometry();
    if (geom.getType() === 'Point') {
        var text = feature.get('text');
        baseTextStyle.text = text + " (" + getClicksByCountry(text) + ")";
        style = new ol.style.Style({
            text: new ol.style.Text(baseTextStyle),
            zIndex: 2
        });
    } else {
        style = highlightStyle;
    }

    return [style];
}