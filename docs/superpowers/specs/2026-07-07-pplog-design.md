# PPLOG Design Specification

**Date:** 2026-07-07  
**Status:** Draft — pending review  
**Author:** Kimi Code (with user input)

## 1. Overview

PPLOG is an Android-native, offline-first mobile app for gym workout exercises and custom plan creation. The app targets Android users who train in environments with poor network coverage and need a lightweight, battery-efficient companion.

### Core value proposition
- Browse a curated exercise library with clear, owned image guides.
- Build custom workout plans via a full onboarding questionnaire.
- Log workouts and track progress offline.
- Download exercise images on demand from Supabase-backed cloud storage.

## 2. Goals and non-goals

### Goals
- Small APK size and low power consumption.
- Smooth, native Android UI/UX with Material 3.
- Full offline operation for core features.
- Clean Google Play Store submission path.
- Advanced version control workflow with protected branches.

### Non-goals
- iOS support in Phase 1.
- Social features, leaderboards, or public sharing.
- Real-time video streaming or camera-based form checking.
- AI-generated final exercise media (only drafts/concepts).

## 3. Architecture

### Platform and stack
- **Language:** Kotlin
- **UI:** Jetpack Compose with Material 3
- **Architecture pattern:** MVVM + Repository pattern
- **Local database:** Room
- **Preferences:** DataStore
- **Background work:** WorkManager
- **Image loading/caching:** Coil
- **Cloud backend:** Supabase (Storage + optional Postgres catalog/backup)

### Data flow
1. Exercise metadata and user plans live in Room on device.
2. Seed data ships with the APK.
3. Exercise images live in Supabase Storage and are downloaded when the user requests them.
4. Downloaded images are cached locally and available offline.
5. Optional cloud backup syncs user plans/settings when online.

## 4. Features

### Phase 1 (MVP)

#### Onboarding / questionnaire
Collect:
- Primary goal (strength, hypertrophy, fat loss, endurance, mobility)
- Training experience (beginner, intermediate, advanced)
- Injuries or limitations
- Available equipment (bodyweight, dumbbells, barbell, machines, cables, kettlebells)
- Days per week available
- Time per session

#### Exercise library
- List exercises with filters: muscle group, equipment, difficulty.
- Detail view per exercise:
  - Name and primary/secondary muscles
  - Step-by-step instructions
  - Equipment needed (clearly labeled)
  - Image guide (downloaded on demand)
  - Difficulty and tags

#### Plan builder
- Generate a custom plan from questionnaire answers.
- Algorithm: rule-based selection from local exercise data, with optional AI text enhancement when online.
- Present plan as days/week with exercises, sets, reps, and rest.

#### Workout session
- Today's workout card from active plan.
- Exercise-by-exercise timer and set/rep logging.
- One-tap image download if not already cached.

#### Settings
- Download manager (view/delete cached images).
- Cloud backup toggle.
- Data export/import.
- Theme and accessibility options.

### Phase 2 (future, not in MVP)
- 3D-rendered multi-angle image sets.
- Animated image carousels for form sequences.
- Advanced analytics and export.

## 5. Media strategy

### Owned media only
PPLOG will ship only original, owned exercise images. No copyrighted videos or images from third-party sites will be used in the app.

### Image production pipeline
1. **Reference:** Use public exercise sites (e.g., Muscle & Strength) as pose and instruction references only.
2. **Create:** Produce images via one of:
   - **3D render pipeline (recommended):** Blender + MakeHuman/SMPL-X. Pose a rigged human model in exact exercise positions and render front/side angles. Guarantees anatomical correctness and consistency.
   - **Real photography:** Hire/record a fitness model for highest trust and realism.
   - **AI-assisted generation:** Leonardo.ai, Ideogram, or Stable Diffusion + ControlNet for concept drafts or supplementary marketing images. Final assets must be reviewed for anatomical accuracy.
3. **Review:** Manual review of every image for correct form and equipment.
4. **Upload:** Approved images uploaded to Supabase Storage and linked to exercise records.
5. **App fetch:** User taps "download guide"; WorkManager fetches and caches the image locally.

### Videos
Videos are out of scope for Phase 1. All instruction will be delivered through text and still images.

## 6. AI tool integration

Kimi can be connected to the following tools via scripts or APIs inside the `tools/media-gen/` directory:

| Tool | Best for | Cost | Connection method |
|---|---|---|---|
| **Pollinations.ai** | Quick concept drafts | Free, no API key | `GET https://image.pollinations.ai/prompt/{prompt}` |
| **Leonardo.ai** | Polished fitness images | Free daily tokens with account | API key / web UI |
| **Ideogram** | Text-in-image, marketing assets | Free credits with personal sign-up | API / web UI |
| **Blender (Python API)** | Anatomically correct 3D renders | Free | Python script |
| **MakeHuman + MHX2** | Base human models | Free | Export to Blender |
| **Stable Diffusion + ControlNet** | Pose-conditioned generation | Free if self-hosted | HTTP API to Automatic1111/ComfyUI |
| **Hugging Face Inference Providers** | SDXL/Flux experiments | Small free monthly credit | Bearer token + HTTP |
| **Replicate** | Flux / SDXL / custom models | Free trial credits | API key + HTTP |

**Recommended workflow:** Use 3D renders or real photos for final production images; use Pollinations/Leonardo/Ideogram only for early concepts and marketing.

## 7. UI/UX structure

- **Splash / onboarding:** Logo → questionnaire → plan preview.
- **Home:** Today's workout, weekly streak, quick-start plan.
- **Explore:** Filterable exercise library.
- **Plan:** Current plan view and plan builder wizard.
- **Workout:** Session view with exercise details, timer, logging.
- **Profile / settings:** Downloads, backup, export, preferences.

## 8. Offline strategy

- Core exercise metadata ships inside the APK as a Room seed database.
- Images are not bundled; they are downloaded on user request.
- Downloaded images are cached in app-private storage and available offline.
- Plan generation runs locally using cached exercise data.
- Optional AI text enhancement only runs when online.

## 9. Security, privacy, and Play Store compliance

- Target the latest stable Android API level.
- Provide a privacy policy even though data is primarily local.
- Declare permissions with justification:
  - `INTERNET` for Supabase and optional backup.
  - `FOREGROUND_SERVICE` and `POST_NOTIFICATIONS` for download manager.
- Ensure all media is owned or properly licensed.
- Support edge-to-edge, dark theme, dynamic colors, and accessibility (TalkBack, large text).
- Content rating: Health & Fitness.

## 10. Version control and GitHub strategy

Repository: `https://github.com/Samant-Patil1/PPLOG`.

### Branch model (Git Flow style)
- **`main`**: Production-ready releases only. Protected — no direct pushes.
- **`develop`**: Integration branch. Feature branches merge here via PR.
- **`feature/<name>`**: One branch per feature or task (e.g., `feature/exercise-catalog`, `feature/plan-builder`).
- **`release/<version>`**: Release preparation, final QA, version bumps.
- **`hotfix/<name>`**: Urgent fixes to `main`.

### Commit hygiene
- Conventional commit messages.
- Small, focused commits.
- Every merge to `develop` and `main` goes through a Pull Request for user review.

## 11. Open questions / decisions

1. Will the 3D render pipeline be built in-house, or will real photography be used for the first exercise set?
2. How many exercises will ship in the MVP seed catalog?
3. Should the plan builder use a purely rule-based algorithm, or should it call a lightweight LLM for plan text when online?

## 12. Next steps

1. User reviews and approves this design document.
2. Create implementation plan via `writing-plans` skill.
3. User reviews implementation plan.
4. Begin Phase 1 implementation in feature branches.
