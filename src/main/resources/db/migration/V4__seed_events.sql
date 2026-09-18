-- V4__seed_events.sql

WITH event_templates (
                      template_no,
                      event_name,
                      event_description,
                      base_price,
                      capacity_ratio,
                      image_url
    ) AS (
    VALUES
        (1,  'Şehrin Ritmi Konseri',
         'Popüler sanatçıların katıldığı yüksek enerjili canlı müzik gecesi.',
         650.00, 0.85,
         'https://images.unsplash.com/photo-1501386761578-eac5c94b800a?auto=format&fit=crop&w=1600&q=80'),

        (2,  'Kahkaha Gecesi',
         'Sevilen komedyenlerin sahne aldığı stand-up gösterisi.',
         400.00, 0.70,
         'https://images.unsplash.com/photo-1527224857830-43a7acc85260?auto=format&fit=crop&w=1600&q=80'),

        (3,  'Bir Yaz Gecesi Rüyası',
         'William Shakespeare eserinden uyarlanan iki perdelik tiyatro oyunu.',
         475.00, 0.65,
         'https://images.unsplash.com/photo-1507676184212-d03ab07a01bf?auto=format&fit=crop&w=1600&q=80'),

        (4,  'Senfoniyle Buluşma',
         'Klasik müziğin sevilen eserlerinden oluşan senfoni konseri.',
         700.00, 0.75,
         'https://images.unsplash.com/photo-1465847899084-d164df4dedc6?auto=format&fit=crop&w=1600&q=80'),

        (5,  'Anadolu Rock Gecesi',
         'Anadolu rock klasiklerini modern yorumlarla buluşturan konser.',
         550.00, 0.85,
         'https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?auto=format&fit=crop&w=1600&q=80'),

        (6,  'Blue Notes Jazz Gecesi',
         'Caz standartları ve özgün bestelerden oluşan özel performans.',
         525.00, 0.65,
         'https://images.unsplash.com/photo-1415201364774-f6f0bb35f28f?auto=format&fit=crop&w=1600&q=80'),

        (7,  'Minik Kaşifler Çocuk Tiyatrosu',
         'Çocukların hayal gücünü ve merakını destekleyen interaktif oyun.',
         250.00, 0.60,
         'https://images.unsplash.com/photo-1596464716127-f2a82984de30?auto=format&fit=crop&w=1600&q=80'),

        (8,  'Geleceğin Teknolojileri Zirvesi',
         'Yapay zeka, yazılım ve teknoloji dünyasındaki yeniliklerin konuşulduğu zirve.',
         900.00, 0.80,
         'https://images.unsplash.com/photo-1540575467063-178a50c2df87?auto=format&fit=crop&w=1600&q=80'),

        (9,  'Modern Dans Gösterisi',
         'Modern dans ve çağdaş sahne sanatlarını bir araya getiren performans.',
         450.00, 0.65,
         'https://images.unsplash.com/photo-1505236858219-8359eb29e329?auto=format&fit=crop&w=1600&q=80'),

        (10, 'İlham Veren Hikâyeler',
         'Alanında başarılı isimlerin deneyimlerini paylaştığı söyleşi etkinliği.',
         325.00, 0.60,
         'https://images.unsplash.com/photo-1475721027785-f74eccf877e2?auto=format&fit=crop&w=1600&q=80'),

        (11, 'Akustik Akşamlar',
         'Sade düzenlemeler ve akustik enstrümanlarla samimi konser deneyimi.',
         425.00, 0.70,
         'https://images.unsplash.com/photo-1516280440614-37939bbacd81?auto=format&fit=crop&w=1600&q=80'),

        (12, 'Şehir Kültür Festivali',
         'Müzik, dans ve sahne gösterilerinin bir araya geldiği kültür festivali.',
         600.00, 0.90,
         'https://images.unsplash.com/photo-1492684223066-81342ee5ff30?auto=format&fit=crop&w=1600&q=80'),

        (13, '90''lar Türkçe Pop Gecesi',
         '90''lı yılların unutulmaz Türkçe pop şarkılarıyla nostaljik konser.',
         550.00, 0.85,
         'https://images.unsplash.com/photo-1524368535928-5b5e00ddc76b?auto=format&fit=crop&w=1600&q=80'),

        (14, 'Ustalardan Türk Sanat Müziği',
         'Türk sanat müziğinin seçkin eserlerinden oluşan özel repertuvar.',
         475.00, 0.70,
         'https://images.unsplash.com/photo-1520523839897-bd0b52f945a0?auto=format&fit=crop&w=1600&q=80'),

        (15, 'Romeo ve Juliet',
         'Shakespeare’in klasik eserinin modern sahne uyarlaması.',
         525.00, 0.70,
         'https://images.unsplash.com/photo-1503095396549-807759245b35?auto=format&fit=crop&w=1600&q=80'),

        (16, 'Film Müzikleri Orkestrası',
         'Sinema tarihinin unutulmaz müziklerinin senfonik yorumları.',
         750.00, 0.80,
         'https://images.unsplash.com/photo-1465847899084-d164df4dedc6?auto=format&fit=crop&w=1600&q=80'),

        (17, 'Alternatif Sahne',
         'Bağımsız müzisyen ve grupların sahne aldığı alternatif müzik gecesi.',
         375.00, 0.70,
         'https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?auto=format&fit=crop&w=1600&q=80'),

        (18, 'Stand-up Açık Mikrofon',
         'Yeni komedyenlerin kısa performanslarla sahne deneyimi yaşadığı gösteri.',
         225.00, 0.55,
         'https://images.unsplash.com/photo-1527224857830-43a7acc85260?auto=format&fit=crop&w=1600&q=80'),

        (19, 'Renkli Dünya Çocuk Müzikali',
         'Müzik, dans ve eğlenceli karakterlerle hazırlanan çocuk müzikali.',
         275.00, 0.65,
         'https://images.unsplash.com/photo-1596464716127-f2a82984de30?auto=format&fit=crop&w=1600&q=80'),

        (20, 'Yapay Zeka ve Girişimcilik',
         'Yapay zeka ürünleri ve teknoloji girişimlerinin geleceği üzerine konferans.',
         800.00, 0.75,
         'https://images.unsplash.com/photo-1488590528505-98d2b5aba04b?auto=format&fit=crop&w=1600&q=80'),

        (21, 'Latin Dans Gecesi',
         'Salsa, bachata ve Latin müzikleri eşliğinde dans gösterisi.',
         400.00, 0.70,
         'https://images.unsplash.com/photo-1505236858219-8359eb29e329?auto=format&fit=crop&w=1600&q=80'),

        (22, 'Edebiyat Buluşmaları',
         'Yazarlar, şairler ve okurların bir araya geldiği edebiyat söyleşisi.',
         200.00, 0.50,
         'https://images.unsplash.com/photo-1475721027785-f74eccf877e2?auto=format&fit=crop&w=1600&q=80'),

        (23, 'Türk Halk Müziği Gecesi',
         'Anadolu’nun farklı yörelerinden türküler ve halk müziği eserleri.',
         425.00, 0.75,
         'https://images.unsplash.com/photo-1516280440614-37939bbacd81?auto=format&fit=crop&w=1600&q=80'),

        (24, 'Gençlik Festivali',
         'Genç sanatçıların konserleri ve sahne performanslarından oluşan festival.',
         500.00, 0.90,
         'https://images.unsplash.com/photo-1492684223066-81342ee5ff30?auto=format&fit=crop&w=1600&q=80'),

        (25, 'Elektronik Müzik Gecesi',
         'DJ performansları ve elektronik müzik setlerinden oluşan gece.',
         600.00, 0.85,
         'https://images.unsplash.com/photo-1501386761578-eac5c94b800a?auto=format&fit=crop&w=1600&q=80'),

        (26, 'Komedi Kulübü',
         'Günlük hayatın eğlenceli tarafını anlatan stand-up gösterisi.',
         375.00, 0.70,
         'https://images.unsplash.com/photo-1527224857830-43a7acc85260?auto=format&fit=crop&w=1600&q=80'),

        (27, 'Hamlet',
         'Shakespeare’in ölümsüz eserinin çağdaş tiyatro uyarlaması.',
         550.00, 0.70,
         'https://images.unsplash.com/photo-1507676184212-d03ab07a01bf?auto=format&fit=crop&w=1600&q=80'),

        (28, 'Piyano Resitali',
         'Klasik ve romantik dönem eserlerinden oluşan solo piyano resitali.',
         500.00, 0.60,
         'https://images.unsplash.com/photo-1520523839897-bd0b52f945a0?auto=format&fit=crop&w=1600&q=80'),

        (29, 'Bağımsız Müzik Sahnesi',
         'Yeni nesil bağımsız müzisyenlerin canlı performansları.',
         350.00, 0.65,
         'https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?auto=format&fit=crop&w=1600&q=80'),

        (30, 'Doğaçlama Tiyatro',
         'Seyirci yönlendirmeleriyle şekillenen interaktif doğaçlama gösterisi.',
         325.00, 0.65,
         'https://images.unsplash.com/photo-1503095396549-807759245b35?auto=format&fit=crop&w=1600&q=80'),

        (31, 'Bilim Şenliği',
         'Çocuklar ve gençler için deneyler, atölyeler ve bilim gösterileri.',
         175.00, 0.75,
         'https://images.unsplash.com/photo-1532094349884-543bc11b234d?auto=format&fit=crop&w=1600&q=80'),

        (32, 'Dijital Dönüşüm Zirvesi',
         'İş dünyasında yazılım, veri ve dijital dönüşüm uygulamalarının konuşulduğu zirve.',
         950.00, 0.80,
         'https://images.unsplash.com/photo-1540575467063-178a50c2df87?auto=format&fit=crop&w=1600&q=80'),

        (33, 'Bale Gala Gecesi',
         'Klasik ve modern bale eserlerinden seçilen özel gala gösterisi.',
         650.00, 0.70,
         'https://images.unsplash.com/photo-1505236858219-8359eb29e329?auto=format&fit=crop&w=1600&q=80'),

        (34, 'Şiir ve Müzik Dinletisi',
         'Şiir okumaları ve canlı müzik performanslarını buluşturan dinleti.',
         275.00, 0.55,
         'https://images.unsplash.com/photo-1475721027785-f74eccf877e2?auto=format&fit=crop&w=1600&q=80'),

        (35, 'Karadeniz Ezgileri',
         'Karadeniz müziğinin sevilen eserleri ve yöresel dans gösterileri.',
         450.00, 0.80,
         'https://images.unsplash.com/photo-1516280440614-37939bbacd81?auto=format&fit=crop&w=1600&q=80'),

        (36, 'Sezon Finali Konseri',
         'Sezonun sevilen sanatçılarını aynı sahnede buluşturan final konseri.',
         800.00, 0.95,
         'https://images.unsplash.com/photo-1492684223066-81342ee5ff30?auto=format&fit=crop&w=1600&q=80')
),

-- V3 migration'ında oluşturduğumuz şehirlerdeki venue'ler.
     seed_venues AS (
         SELECT
             v.id,
             v.name,
             v.city,
             v.capacity,
             ROW_NUMBER() OVER (ORDER BY v.city, v.name)::integer AS venue_no
         FROM venues v
         WHERE v.city IN (
                          'İstanbul',
                          'Ankara',
                          'İzmir',
                          'Samsun',
                          'Çorum'
             )
     ),

-- Sonraki ilk pazartesiden başlayarak:
-- pazartesi, çarşamba ve cumartesi.
     event_schedule AS (
         SELECT
             (week_no * 3 + day_config.day_sequence)::integer AS slot_no,
             week_no,
             day_config.day_offset,
             day_config.start_hour
         FROM generate_series(0, 11) AS week_series(week_no)
                  CROSS JOIN (
             VALUES
                 (1, 0, 20), -- Pazartesi 20:00
                 (2, 2, 20), -- Çarşamba 20:00
                 (3, 5, 19)  -- Cumartesi 19:00
         ) AS day_config(day_sequence, day_offset, start_hour)
     ),

     candidate_events AS (
         SELECT
             template.event_name AS name,

             template.event_description
                 || ' Etkinlik '
                 || venue.city
                 || ' şehrindeki '
                 || venue.name
                 || ' mekânında gerçekleştirilecektir.' AS description,

             template.image_url AS event_image_url,

             (
                 (
                             CURRENT_DATE
                         + (8 - EXTRACT(ISODOW FROM CURRENT_DATE)::integer)
                         + (schedule.week_no * 7)
                         + schedule.day_offset
                     )::timestamp
            + make_interval(hours => schedule.start_hour)
     ) AT TIME ZONE 'Europe/Istanbul' AS event_date,

        ROUND(
            template.base_price
            * (1 + (((venue.venue_no - 1) % 5) * 0.05)),
            2
        ) AS price,

        LEAST(
            venue.capacity,
            GREATEST(
                50,
                FLOOR(venue.capacity * template.capacity_ratio)::integer
            )
        ) AS event_capacity,

        'ACTIVE' AS status,
        venue.id AS venue_id

FROM seed_venues venue
    CROSS JOIN event_schedule schedule
    JOIN event_templates template
ON template.template_no =
    ((schedule.slot_no + venue.venue_no - 2) % 36) + 1
    )

INSERT INTO events (
    name,
    description,
    event_image_url,
    event_date,
    price,
    total_capacity,
    available_capacity,
    status,
    venue_id
)
SELECT
    candidate.name,
    candidate.description,
    candidate.event_image_url,
    candidate.event_date,
    candidate.price,
    candidate.event_capacity,
    candidate.event_capacity,
    candidate.status,
    candidate.venue_id
FROM candidate_events candidate
WHERE NOT EXISTS (
    SELECT 1
    FROM events existing
    WHERE existing.venue_id = candidate.venue_id
      AND (
        existing.event_date AT TIME ZONE 'Europe/Istanbul'
        )::date = (
        candidate.event_date AT TIME ZONE 'Europe/Istanbul'
        )::date
);