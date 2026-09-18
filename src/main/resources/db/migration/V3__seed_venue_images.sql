-- V3__seed_venues.sql
-- Kapasiteler seed/demo ortamı için yaklaşık değerlerdir.
-- Görseller gerçek mekânlara ait harici kaynak URL'leridir.

WITH seed_venues(name, city, capacity, image_url) AS
         (
             VALUES
                 -- =====================================================
                 -- İSTANBUL
                 -- =====================================================
                 (
                     'Zorlu PSM Turkcell Sahnesi',
                     'İstanbul',
                     2200,
                     'https://image.passo.com.tr/api/r/tr/p/venue/30052025112834-zorlupsm_turkcellsahnesi2.jpg'
                 ),
                 (
                     'Volkswagen Arena',
                     'İstanbul',
                     5800,
                     'https://api.pozitifmuzik.com/images/static-page/8032446a2db642a9ae67287d6f50170a/base.png'
                 ),
                 (
                     'Harbiye Cemil Topuzlu Açıkhava Tiyatrosu',
                     'İstanbul',
                     3972,
                     'https://www.denizbank.com/medium/GalleryImage-Image-69-2x.vsf'
                 ),
                 (
                     'Atatürk Kültür Merkezi Türk Telekom Opera Salonu',
                     'İstanbul',
                     2040,
                     'https://www.tabanlioglu.com/wp-content/uploads/2019/01/TA_TABANLIOGLU_ARCHITECTS_AKM_EMRE_DORTER_RINO1384-840x560.jpg'
                 ),
                 (
                     'İstanbul Kongre Merkezi Harbiye Salonu',
                     'İstanbul',
                     3700,
                     'https://www.nenerede.com.tr/wp-content/uploads/2017/09/%C4%B0stanbul-Kongre-Merkezi-Hakk%C4%B1nda-848x566.jpg'
                 ),
                 (
                     'Cemal Reşit Rey Konser Salonu',
                     'İstanbul',
                     860,
                     'https://cdnuploads.aa.com.tr/uploads/Contents/2025/01/07/thumbs_b_c_2f96af10ca97a6a92fab1baf885e1d47.jpg'
                 ),
                 (
                     'Maximum Uniq Açıkhava',
                     'İstanbul',
                     1500,
                     'https://img22.maximumuniq.com.tr/uploads/CAN_8294_fc7a42ecb7.webp'
                 ),
                 (
                     'KüçükÇiftlik Park',
                     'İstanbul',
                     17000,
                     'https://img-s1.onedio.com/id-686259dfcf529e99186a696c/rev-0/w-600/h-670/f-jpg/s-ff6b786d4ee0ce894b93bd3d0f52b270bb8481c0.jpg'
                 ),

                 -- =====================================================
                 -- ANKARA
                 -- =====================================================
                 (
                     'CSO Ada Ankara Ana Salon',
                     'Ankara',
                     2023,
                     'https://csoadaankara.gov.tr/images/AnaSalon.jpg'
                 ),
                 (
                     'ATO Congresium',
                     'Ankara',
                     3100,
                     'https://www.aydinlatma.org/wp-content/uploads/2020/01/ATO-Congresium.jpg'
                 ),
                 (
                     'MEB Şura Salonu',
                     'Ankara',
                     800,
                     'https://tiyatrolar.com.tr/files/location/m/meb-sura-salonu/image/meb-sura-salonu.jpg'
                 ),
                 (
                     'Ankara Atatürk Spor Salonu',
                     'Ankara',
                     4500,
                     'https://yenibakishabercom.teimg.com/yenibakishaber-com/uploads/2024/02/ataturk-spor-salonu-ankara-nerede-ataturk-spor-salonu-nasil-gidilir-1.jpg'
                 ),
                 (
                     'Nazım Hikmet Kongre ve Sanat Merkezi',
                     'Ankara',
                     1500,
                     'https://yenimahalle2025.albinasoft.com/Dosyalar/FotoAlbumleri/32/32_9_3ac6d20c-3360-4a04-9f5b-4106b9bad438.jpg'
                 ),
                 (
                     'CerModern Açık Hava Sahnesi',
                     'Ankara',
                     2000,
                     'https://sinpas.com.tr/Uploads/assets/cms/Blog/00002/00002532_ng55kfmbjtuj2.png'
                 ),
                 (
                     'Bilkent Odeon',
                     'Ankara',
                     4000,
                     'https://www.tepe.com.tr/assets/images/content/bilkent-odeon.jpg'
                 ),
                 (
                     'ODTÜ Kemal Kurdaş Salonu',
                     'Ankara',
                     837,
                     'https://kkm.metu.edu.tr/en/system/files/styles/gallery_image/private/gallery/kemal-2.jpg?itok=kEE5Gpol'
                 ),

                 -- =====================================================
                 -- İZMİR
                 -- =====================================================
                 (
                     'Ahmed Adnan Saygun Sanat Merkezi Büyük Salon',
                     'İzmir',
                     1130,
                     'https://visitizmiryonetim.izmir.bel.tr/YuklenenDosyalar/DestinasyonFotograflari/1024/15012021115941004.jpg'
                 ),
                 (
                     'Kültürpark Açıkhava Tiyatrosu',
                     'İzmir',
                     2870,
                     'https://www.izmir.art/imgServ/img/uploads/e2yyko.jpg'
                 ),
                 (
                     'İzmir Arena',
                     'İzmir',
                     2500,
                     'https://www.izmirarena.com/img/fotogaleri/buyuk/festival-mekani4-1.jpg'
                 ),
                 (
                     'Bostanlı Suat Taşer Sanat Merkezi',
                     'İzmir',
                     600,
                     'https://www.bizimizmir.net/images_haberler/2019/7/10/haber-2019-07-10-1562755926004-3.jpg'
                 ),
                 (
                     'İsmet İnönü Sanat Merkezi',
                     'İzmir',
                     752,
                     'https://visitizmiryonetim.izmir.bel.tr/YuklenenDosyalar/DestinasyonFotograflari/1024/08022021_033048_etkinlikmerkezi7.jpg'
                 ),
                 (
                     'Tepekule Kongre ve Sergi Merkezi',
                     'İzmir',
                     1000,
                     'https://img.expodmc.com/exhibition/img/22/01f9d481168c64abcaaaf6a39743ffd1.jpg'
                 ),
                 (
                     'Hikmet Şimşek Sanat Merkezi',
                     'İzmir',
                     517,
                     'https://okuldisiogrenme-cdn-small.eba.gov.tr/uploads/places/68c7f110e0998.png'
                 ),
                 (
                     'Bornova Aşık Veysel Açıkhava Tiyatrosu',
                     'İzmir',
                     5000,
                     'https://www.izmir.bel.tr/YuklenenDosyalar/Haberler/Buyuk/01082018_10463_0.jpg'
                 ),

                 -- =====================================================
                 -- SAMSUN
                 -- =====================================================
                 (
                     'Samsun Büyükşehir Belediyesi Sanat Merkezi',
                     'Samsun',
                     550,
                     'https://atakumplus.com/_next/image?q=75&url=https%3A%2F%2Fcdn.atakumplus.com%2Fm7yn60px.jpeg&w=3840'
                 ),
                 (
                     'Atatürk Kültür Merkezi Aydın Gün Sahnesi',
                     'Samsun',
                     509,
                     'https://okuldisiogrenme.eba.gov.tr/uploads/places/68e8fe863cd7a.jpg'
                 ),
                 (
                     'Ondokuz Mayıs Üniversitesi Atatürk Kongre ve Kültür Merkezi',
                     'Samsun',
                     1000,
                     'https://www.omu.edu.tr/sites/default/files/files/prof._dr._kaya_tuncer_caglayan_ondokuz_mayis_universitesi_kurtulus_mucadelesinden_ilham_alinarak_kurulmustur/omu_4.jpeg'
                 ),
                 (
                     'Doğupark Amfi Tiyatro',
                     'Samsun',
                     5000,
                     'https://i.samsunsonhaber.com/resimler/25082022/6612fee6m.jpg'
                 ),
                 (
                     'Samsun 19 Mayıs Stadyumu',
                     'Samsun',
                     33919,
                     'https://gunaydinsamsuncom.teimg.com/gunaydinsamsun-com/uploads/2023/06/19mayis-stadi-5.jpg'
                 ),
                 (
                     'Canik Kültür Merkezi',
                     'Samsun',
                     800,
                     'https://static.wixstatic.com/media/a3bbcf_6ffe6efa3cc64a95b2a0450d87180d9d~mv2.png/v1/fill/w_727%2Ch_866%2Cal_c%2Cq_90%2Cenc_auto/a3bbcf_6ffe6efa3cc64a95b2a0450d87180d9d~mv2.png'
                 ),
                 (
                     'Atakum Belediyesi Ata Sahne',
                     'Samsun',
                     550,
                     'https://www.niksardanismend.com/images/files/2024/03/6602c1d8b0e53.jpg'
                 ),
                 (
                     'Mustafa Dağıstanlı Spor Salonu',
                     'Samsun',
                     2000,
                     'https://static.wixstatic.com/media/274477_889268a791b7423690bb15ea2aa39c4a~mv2.jpg/v1/fill/w_980%2Ch_653%2Cal_c%2Cq_85%2Cusm_0.66_1.00_0.01%2Cenc_avif%2Cquality_auto/274477_889268a791b7423690bb15ea2aa39c4a~mv2.jpg'
                 ),

                 -- =====================================================
                 -- ÇORUM
                 -- =====================================================
                 (
                     'Çorum Devlet Tiyatrosu',
                     'Çorum',
                     418,
                     'https://okuldisiogrenme-cdn-small.eba.gov.tr/uploads/places/698f1c1e09019.jpg'
                 ),
                 (
                     'Çorum Belediyesi Buhara Kültür Merkezi',
                     'Çorum',
                     500,
                     'https://okuldisiogrenme-cdn-small.eba.gov.tr/uploads/places/68de5ab26918c.jpg'
                 ),
                 (
                     'Hitit Üniversitesi Ethem Erkoç Konferans Salonu',
                     'Çorum',
                     750,
                     'https://www.kesinkarar.com/olds/kesinkarar-com/wp/uploads/2023/12/HITUde-insan-haklari-konusuldu-3-1024x1024.webp'
                 ),
                 (
                     'Çorum TSO Kültür ve Konferans Salonu',
                     'Çorum',
                     485,
                     'https://yaylahabercomtr.teimg.com/crop/1280x720/yaylahaber-com-tr/uploads/2025/12/tsokonferanssalonu-1.jpg'
                 ),
                 (
                     'Çorum Belediyesi Spor Salonu',
                     'Çorum',
                     3500,
                     'https://www.corum.bel.tr/public/uploads/2023/10/spor-salonu-5_large.jpg?v=0b98a36e794fccf7ef2e0190081463e1a168c4d1'
                 ),
                 (
                     'Kadeş Barış Meydanı',
                     'Çorum',
                     5000,
                     'https://i0.wp.com/www.corumgundemi.net/wp-content/uploads/2021/08/Still0805_00001.jpg?resize=780%2C439&ssl=1'
                 ),
                 (
                     'Çorum Şehir Stadyumu',
                     'Çorum',
                     15000,
                     'https://stadiony.net/pictures/stadiums/tur/corum_sehir_stadyumu/corum_sehir_stadyumu01.jpg'
                 )
         )
INSERT INTO venues
(
    name,
    city,
    capacity,
    image_url
)
SELECT
    seed.name,
    seed.city,
    seed.capacity,
    seed.image_url
FROM seed_venues seed
WHERE NOT EXISTS
          (
              SELECT 1
              FROM venues existing
              WHERE existing.name = seed.name
                AND existing.city = seed.city
          );