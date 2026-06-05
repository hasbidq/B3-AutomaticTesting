@Orang2
Feature: Tugas Orang 2 (TC-FR-1.8 & TC-FR-6.0.7)

  Background:
    Given User membuka browser
    And User berada di halaman login JTK Learn

  @TC-FR-1.8
  Scenario: Login sebagai pelajar gagal
    When User memasukkan email "wronguser@example.com" dan password "wrongpass"
    And User menekan tombol login
    Then Muncul pesan error login "Alamat email atau kata sandi tidak sesuai"
    And User masih berada di halaman login

  @TC-FR-6.0.7
  Scenario: Gagal daftar course karena enroll key salah
    When User memasukkan email "muhammad.hasbi.tif423@polban.ac.id" dan password "gatot123"
    And User menekan tombol login
    And User mencari course yang tersedia dan membuka halaman overview-nya
    And User memasukkan enrollment key "kuncisalah999"
    And User menekan tombol daftar
    Then Muncul pesan error pendaftaran "Kode pendaftaran salah"
