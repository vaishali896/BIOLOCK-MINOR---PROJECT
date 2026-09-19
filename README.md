# BioLock: Behavioral Biometric Authentication System
> **LNCT Minor Project** | Department of Computer Science & Engineering

BioLock is a hardware-free, multi-factor authentication (MFA) engine built with **Spring Boot 3** and **Java 17**. It validates user identity by analyzing neuro-muscular typing cadence (dwell time and flight latency) alongside standard password credentials.

---

## Key Features

- **Zero-Hardware MFA:** Analyzes behavioral typing rhythms using standard keyboard input without biometric sensors.
- **Euclidean Distance Evaluation:** Measures statistical divergence between enrolled baselines and real-time login attempts in $n$-dimensional space.
- **Dynamic Adaptive Thresholding:** Automatically scales distance tolerance based on passphrase length ($\sqrt{N} \times \text{Base Tolerance}$).
- **In-Memory Telemetry Pipeline:** Persists user credentials and biometric profiles via Spring Data JPA and an embedded H2 database.
- **Glassmorphism UI:** Interactive client dashboard with live keystroke capture and latency feedback.

---

## Architecture & Data Flow