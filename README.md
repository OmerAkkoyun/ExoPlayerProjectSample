# MovieListApp 🎬
MovieListApp, kullanıcıların popüler, çıkış tarihine göre ve yüksek gelirli filmleri listeleyip detaylarına erişebilecekleri, modern Android teknolojileri kullanılarak geliştirilmiş bir uygulamadır.

## 🚀 Özellikler


- Film Listeleri: Popüler, çıkış tarihine göre ve yüksek gelirli film listeleri
- Film Detayları: Filmlerin açıklama, puan ve çıkış tarihi gibi bilgileri
- Swipe-to-Refresh: Listeyi yenilemek için kaydırarak yenileme özelliği
- Tablet ve Yatay Mod Desteği: Ekran çözünürlüğüne uyumlu, yatay ve tablet modlarına uygun tasarım
- ExoPlayer ile Film Fragmanı Oynatma: Detay ekranında fragmanlar (test videosu)
- Offline Caching: Veriler internet bağlantısı olmadığında önceden kaydedilmiş veriler


## 🛠️ Kullanılan Teknolojiler

- Kotlin - Programlama dili
- MVVM & Clean Architecture - Mimarisi
- Hilt - Dependency Injection
- Retrofit - API çağrıları için
- Paging 3 - Sayfalama ve veri yönetimi
- Coil - Görsel yükleme
- ExoPlayer - Video oynatıcı
- SwipeRefreshLayout - Kaydırarak yenileme özelliği
- Security Crypto - Güvenli veri saklama
- Asenkron işlemler için **Coroutines** ve **LiveData** kullanımı
- **Hilt** ile bağımlılık enjeksiyonu.
- **Room** kullanılarak yerel veri cache depolama.
- **ViewBinding** ile UI bileşenlerine güvenli erişim.

## 📐 Mimarisi

Bu proje, **MVVM Clean Architecture** mimarisini takip eder. Bu mimari, uygulamanın farklı katmanlar arasında net bir ayrım sağlayarak daha test edilebilir ve sürdürülebilir bir yapı sunar.

### Katmanlar:
- **Presentation Layer**: UI ile doğrudan iletişim kurar. `ViewModel`, UI verilerini yönetir ve günceller.
- **Domain Layer**: Uygulamanın iş kurallarını içerir. `UseCase`'ler iş mantığını kapsar.
- **Data Layer**: Veri kaynaklarını yönetir. WebSocket ve Room gibi veri sağlayıcıları burada bulunur.
- 

## 📷🤩 Ekran Görüntüleri

![Screenshot_1730752460](https://github.com/user-attachments/assets/047407c1-de89-409a-90e1-405b34d2fc40)
![4](https://github.com/user-attachments/assets/2b5d2abc-75df-4f20-8c3f-ccfddc2d933f)
![3](https://github.com/user-attachments/assets/34f1a637-666a-41ef-9abd-a8528030009a)
![Screenshot_1730752928](https://github.com/user-attachments/assets/7610fbeb-b228-423e-b866-e2e805d1fa90)
![Screenshot_1730752899](https://github.com/user-attachments/assets/287fd734-feb3-47d4-8913-ce28038ffbf9)
![Screenshot_1730752856](https://github.com/user-attachments/assets/dc861d77-5523-4ba3-8915-d6bb1199025b)
![Screenshot_1730752851](https://github.com/user-attachments/assets/96a2974b-691a-426d-aa32-40de5fda6bde)
![Screenshot_1730752753](https://github.com/user-attachments/assets/d5cc86b3-06f5-4aa4-94ae-c01d229942e0)
![Screenshot_1730752720](https://github.com/user-attachments/assets/3580d4c6-cd73-4762-93f3-df499b4f1bb5)
![Screenshot_1730752704](https://github.com/user-attachments/assets/f541a7d6-6a07-42e4-924d-8bf92593669a)
![Screenshot_1730752608](https://github.com/user-attachments/assets/f97707f2-39a9-46c7-84fb-383c21423bee)
![Screenshot_1730752545](https://github.com/user-attachments/assets/dc7930d3-e493-4a0a-99ad-2b026dc5dfd9)
![Screenshot_1730752481](https://github.com/user-attachments/assets/9edd7b86-b36f-4bfa-ac22-ff3b852dbed3)







