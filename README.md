# 🌟 Luminous AI: Personal AI Companion
**Powered by Gemini 3.0 — Create, Customize, Converse.**

Luminous AI (formerly Miya) is an advanced AI companion app that allows users to create human-like AI personalities. By leveraging **Gemini 3.0**, Luminous AI provides emotional, natural, and highly personalized responses tailored to user-defined identities.

---

## 🚀 Gemini 3.0 Implementation
For the **Gemini 3 Global Hackathon**, we integrated the latest model family to move beyond generic chatbots. Luminous AI utilizes:

- **Model:** `gemini-3-flash`
- **Thinking Level:** Set to `medium` for balanced, natural-sounding emotional reasoning.
- **Context Persistence:** We utilize **Thought Signatures** (persistent reasoning context) to ensure that the AI "remembers" its custom personality and name across long conversations without losing its unique voice.
- **Intent Recognition:** Gemini 3.0's advanced reasoning is used to interpret user emotions and respond with a "human-touch" rather than robotic text.

### 📍 Core AI Logic Location:
The primary Gemini 3 integration and prompt-engineering logic can be found at:  
`app/src/main/java/luminous/organisation/Miya/ChatActivity.java`  
*(Note: Prototyping artifacts and legacy code from the Miya-to-Luminous evolution are present in the repo but do not affect the Gemini 3 core).*

---

## ✨ Detailed Features
- **👤 Custom Personas:** Define your AI’s name and personality in Settings.
- **❤️ Emotional Intelligence:** Responses are designed to be natural and conversational.
- **🔊 Voice Control:** Built-in Text-to-Speech (TTS) with pitch and rate customization.
- **📚 AI Library:** Discover and download pre-set AI models.
- **📱 Modern UX:** Dark/Light mode support, multiline texting, and granular message copying.

---

## 🛠 Tech Stack
- **Language:** Kotlin / Java (Android 5.0+)
- **AI SDK:** Google AI Studio (Gemini 3 API)
- **Voice Engine:** Android Text-To-Speech API
- **Workflow:** Developed using a 'Vibe Coding' methodology with Gemini 3.
