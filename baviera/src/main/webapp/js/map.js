var map = L.map('mapid').setView([37.72150548, -3.97460461], 15);

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
}).addTo(map);

L.marker([37.72150548, -3.97460461]).addTo(map)
    .bindPopup('Restaurante Baviera')
    .openPopup();
    
map.setZoom(20)