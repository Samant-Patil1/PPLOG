# PPLOG UI Redesign & Exercise Detail Popup — Design Document

**Date:** 2026-07-11  
**Status:** Approved  
**Approach:** A&B combo — Material 3 Expressive + Dark Athletic/Premium  

---

## 1. Purpose

Modernize the PPLOG Android app UI and replace the full-screen exercise detail drill-down with a modal bottom-sheet popup that shows step-by-step procedure and form tips when the user taps an exercise name.

---

## 2. Visual Direction

### 2.1 Color Palette

| Token | Hex | Usage |
|-------|-----|-------|
| Background | `#0F1419` | Screen background |
| Surface | `#1A1F25` | Cards, sheets, dialogs |
| Primary | `#4ADE80` | CTAs, active nav, success states |
| Secondary | `#FB923C` | Highlights, streaks, warnings |
| Error | `#F87171` | Errors |
| On Surface | `#F1F5F9` | Primary text |
| On Surface Muted | `#94A3B8` | Secondary text, placeholders |

### 2.2 Typography

- Primary font: system `Roboto` on Android, with a sportier weight scale.
- Scale: 32sp hero, 24sp screen titles, 18sp card titles, 14sp body, 12sp captions.
- Use `FontWeight.SemiBold` for headings and `FontWeight.Medium` for labels.

### 2.3 Shape & Elevation

- Cards: 16dp rounded corners.
- Bottom sheets: 24dp top corners.
- Buttons/chips/filters: 8dp rounded corners.
- Soft shadows only; avoid glassmorphism to keep GPU cost low.

### 2.4 Motion

- Screen enter/exit: shared-axis slide + fade (Material Motion).
- List items: staggered fade-in.
- Bottom sheet: spring-based expand/collapse.
- Keep animations under 300ms and respect reduced-motion settings.

---

## 3. Navigation & Screen Architecture

### 3.1 Bottom Navigation

Replace the current `BottomAppBar` with a four-tab `NavigationBar`:

1. **Home** — filled/outline `Home` icon
2. **Explore** — filled/outline `Search` icon
3. **Plan** — filled/outline `DateRange` icon
4. **Settings** — filled/outline `Settings` icon

Selected tab uses primary color and filled icon; unselected uses muted color and outlined icon.

### 3.2 Screen-by-Screen Changes

| Screen | Redesign Summary |
|--------|------------------|
| Home | Hero greeting card, weekly streak summary, today's workout CTA card, quick stats row. |
| Explore | Search bar, collapsible filter chips (muscle, equipment, difficulty), exercise cards with thumbnail placeholders, muscle/equipment badges. |
| Plan | Timeline-style day cards, expandable exercise lists, rebuild/edit actions, FAB for "Build new plan". |
| Workout | Progress ring, checklist cards per exercise, rest timer, completion state. |
| Settings | Grouped preference cards, offline status, image cache usage, account/sync status. |
| Onboarding | Horizontal pager with illustration placeholders and profile questions. |

### 3.3 Transitions

- `NavHost` uses Material Motion `sharedAxis` transitions between top-level screens.
- Modal bottom sheets handle exercise detail and quick plan actions.

---

## 4. Exercise Detail Popup Card

### 4.1 Trigger

- Tapping an exercise **name** or anywhere on its card opens the popup.
- Long-press shows a context menu: "Add to plan", "Mark favorite".

### 4.2 Layout (Modal Bottom Sheet)

1. **Hero** — exercise image/video thumbnail with rounded top corners and gradient scrim.
2. **Header** — exercise name, difficulty badge, muscle target chips, equipment chips.
3. **Steps & Procedure** — numbered, checkable steps from `Exercise.instructions`.
4. **Form Tips** — bullet list for optimum form and effectiveness (maps to existing `Exercise.tips`).
5. **Actions** — "Download media", "Add to today's workout", "Close".

### 4.3 Behavior

- Sheet initially peeks at ~60% screen height; draggable to full screen.
- Swipe down or tap Close dismisses it.
- Shared element transition from exercise card thumbnail to sheet hero.
- Images/videos load only when the user requests download (existing offline-first rule).

### 4.4 Data Source

- Steps and tips come from `ExerciseEntity`.
- If `formTips` is empty, show a compact placeholder with a sync hint.

---

## 5. Component Architecture

### 5.1 New Shared Components

- `PPLOGBottomBar` — styled `NavigationBar`.
- `HeroCard` — large gradient-backed card for Home/Explore hero sections.
- `StatRow` — horizontally scrollable stat pills.
- `ExerciseListItem` — redesigned card with thumbnail, badges, and popup trigger.
- `ExerciseDetailBottomSheet` — modal sheet for exercise steps and tips.
- `FilterSection` — collapsible header + chip group.
- `DayCard` — expandable plan day card.
- `WorkoutSetCard` — checkbox-based set tracker.

### 5.2 Theme Updates

- Extend `Color.kt` with new palette tokens.
- Update `Theme.kt` with custom dark scheme; keep light scheme minimal or derive from dark.
- Update `Type.kt` with the full Material 3 type scale.

---

## 6. Data Flow

- UI state continues to flow through existing ViewModels (`HomeViewModel`, `ExploreViewModel`, `PlanViewModel`, `WorkoutViewModel`).
- The popup receives an `exerciseId` and fetches the exercise via `ExerciseRepository.getExercise(id)`.
- Existing `Exercise.tips` field powers the Form Tips section.
- Offline-first: all exercise content ships with the app via `SeedDatabaseWorker`.

---

## 7. Error Handling

- Empty popup content shows a friendly placeholder: "Full instructions for this exercise are being prepared."
- Image load errors show a tinted placeholder icon and retry action.
- Download failures surface a snackbar with "Retry".

---

## 8. Testing

- Update existing unit tests for new `Exercise` model shape.
- Add Compose UI tests for bottom sheet open/close and step visibility.
- Verify navigation between tabs.
- Run `./gradlew :app:assembleDebug` and `./gradlew :app:testDebugUnitTest` before merge.

---

## 9. Accessibility & Play Store Considerations

- Minimum touch target 48dp.
- Color contrast ≥ 4.5:1 for text.
- Content descriptions for all icons and images.
- Respect `TalkBack` traversal order in bottom sheet.
- No camera permission (pose detection already removed).
- Keep APK size reasonable; use vector icons and WebP images where possible.

---

## 10. Implementation Phases

1. **Theme & tokens** — colors, typography, shapes.
2. **Navigation** — bottom bar + transitions.
3. **Shared components** — cards, badges, filter sections.
4. **Screen redesigns** — Home, Explore, Plan, Workout, Settings, Onboarding.
5. **Exercise popup** — bottom sheet, data model updates, form tips.
6. **Tests & QA** — unit tests, UI tests, build verification.
7. **Branch merge** — feature branch → develop → release.
