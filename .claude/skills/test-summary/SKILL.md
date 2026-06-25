---
name: test-summary
description: "Lance la suite de tests Maven et produit un résumé structuré. Utiliser quand l'utilisateur demande de lancer ou vérifier les tests."
---

# Test Summary

Lance tous les tests Maven et produit un résumé structuré des résultats.

## Steps

1. Run `mvnw.cmd test` in the project root and capture the output.

2. Parse the output and report:
   - Total tests run, passed, failed, skipped
   - List of any failing tests with their class name and failure reason (one line each)
   - Whether the build is GREEN (all pass) or RED (at least one failure)

3. If all tests pass, suggest the next logical action based on recent `git status` (e.g. commit, push, or add more tests).

4. If any test fails, identify the most likely root cause and propose a fix — check the relevant source file before answering.

Respond in French. Keep the summary concise (≤ 15 lines).
