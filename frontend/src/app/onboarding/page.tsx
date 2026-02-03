"use client";

import { useEffect, useState } from "react";
import { onboardingApi } from "@/lib/api";
import { useRouter } from "next/navigation";
import { motion, AnimatePresence } from "framer-motion";

export default function OnboardingPage() {
    const [questions, setQuestions] = useState<string[]>([]);
    const [currentStep, setCurrentStep] = useState(-1); // -1 for introduction
    const [answers, setAnswers] = useState<Record<string, string>>({});
    const [currentAnswer, setCurrentAnswer] = useState("");
    const router = useRouter();

    useEffect(() => {
        onboardingApi.getQuestions().then((res) => setQuestions(res.data));
        onboardingApi.getStatus().then((res) => {
            if (res.data) router.push("/journal");
        });
    }, [router]);

    const handleNext = () => {
        if (currentStep === -1) {
            setCurrentStep(0);
            return;
        }

        const newAnswers = { ...answers, [currentStep]: currentAnswer };
        setAnswers(newAnswers);
        setCurrentAnswer("");

        if (currentStep < questions.length - 1) {
            setCurrentStep(currentStep + 1);
        } else {
            // Final submission
            onboardingApi.submit(newAnswers).then(() => {
                router.push("/journal");
            });
        }
    };

    return (
        <div className="flex flex-col items-center justify-center min-h-screen p-8 text-center bg-[#0a0a0a]">
            <AnimatePresence mode="wait">
                <motion.div
                    key={currentStep}
                    initial={{ opacity: 0, y: 10 }}
                    animate={{ opacity: 1, y: 0 }}
                    exit={{ opacity: 0, y: -10 }}
                    transition={{ duration: 0.8, ease: "easeInOut" }}
                    className="max-w-2xl w-full"
                >
                    {currentStep === -1 ? (
                        <div className="space-y-6">
                            <h1 className="text-4xl font-bold tracking-tighter uppercase text-white">Silence</h1>
                            <p className="text-muted text-lg">
                                This space is not for performance. It is for observation.
                                Before you enter, we must define the manifesto of your silence.
                            </p>
                            <button
                                onClick={handleNext}
                                className="px-8 py-3 bg-white text-black font-bold uppercase hover:bg-muted transition-colors"
                            >
                                Begin
                            </button>
                        </div>
                    ) : (
                        <div className="space-y-8">
                            <div className="text-muted text-sm uppercase tracking-widest text-mono">
                                Step {currentStep + 1} of {questions.length}
                            </div>
                            <h2 className="text-2xl font-light leading-snug italic">
                                "{questions[currentStep]}"
                            </h2>
                            <textarea
                                autoFocus
                                value={currentAnswer}
                                onChange={(e) => setCurrentAnswer(e.target.value)}
                                placeholder="Type your truth..."
                                className="w-full bg-transparent border-b border-border text-center text-xl p-4 focus:outline-none focus:border-accent transition-colors resize-none h-32"
                                onKeyDown={(e) => {
                                    if (e.key === "Enter" && !e.shiftKey && currentAnswer.trim()) {
                                        e.preventDefault();
                                        handleNext();
                                    }
                                }}
                            />
                            <div className="flex justify-center">
                                <button
                                    disabled={!currentAnswer.trim()}
                                    onClick={handleNext}
                                    className="px-10 py-2 border border-white uppercase text-sm font-bold hover:bg-white hover:text-black transition-all disabled:opacity-20 disabled:hover:bg-transparent disabled:hover:text-white"
                                >
                                    {currentStep === questions.length - 1 ? "Consign to the void" : "Next"}
                                </button>
                            </div>
                        </div>
                    )}
                </motion.div>
            </AnimatePresence>
        </div>
    );
}
