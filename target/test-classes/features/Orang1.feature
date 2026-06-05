@Orang1
Feature: Tugas Orang 1 (TC-FR-1.2 & TC-FR-6.0.2)

  Background:
    Given User membuka browser
    And User berada di halaman login JTK Learn

  @TC-FR-1.2
  Scenario: Login sebagai pelajar berhasil
    When User memasukkan email "pelajar1@example.com" dan password "pelajar1"
    And User menekan tombol login
    Then User berhasil masuk ke halaman dashboard pelajar

  @TC-FR-6.0.2
  Scenario: Berhasil daftar course
    When User memasukkan email "pelajar1@example.com" dan password "pelajar1"
    And User menekan tombol login
    And User membuka halaman Course Overview untuk course "Rongawi History"
    And User memasukkan enrollment key "rongawi101"
    And User menekan tombol daftar
    Then Muncul pesan sukses pendaftaran course
