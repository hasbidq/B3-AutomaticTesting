@Orang3
Feature: Tugas Orang 3 (TC-FR-2.2 & TC-FR-6.0.8)

  Background:
    Given User membuka browser
    And User berada di halaman login JTK Learn

  @TC-FR-2.2
  Scenario: Logout sebagai pelajar berhasil
    When User memasukkan email "pelajar1@example.com" dan password "pelajar1"
    And User menekan tombol login
    And User menekan menu profile
    And User menekan tombol logout
    Then User berhasil keluar dan diarahkan ke halaman login

  @TC-FR-6.0.8
  Scenario: Gagal daftar course karena enroll key tidak diisi
    When User memasukkan email "pelajar1@example.com" dan password "pelajar1"
    And User menekan tombol login
    And User membuka halaman Course Overview untuk course "Rongawi History"
    And User membiarkan enrollment key kosong
    And User menekan tombol daftar
    Then Sistem meminta enrollment key diisi
