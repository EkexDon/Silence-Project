"use client";

import { useEffect, useState } from "react";
import { entryApi, intelligenceApi, onboardingApi } from "@/lib/api";
import { useRouter } from "next/navigation";
import { motion, AnimatePresence } from "framer-motion";
import { Sparkles } from "lucide-react";

interface Entry {
    id: number;
    content: string;
    entryDate: string;
    createdAt: string;
    aiFeedback: string;
    moodScore: number;
}

export default function JournalPage() {
    const [entries, setEntries] = useState<Entry[]>([]);
    const [hasWrittenToday, setHasWrittenToday] = useState(false);
    const [content, setContent] = useState("");
    const [summary, setSummary] = useState<string | null>(null);
    const [isLoading, setIsLoading] = useState(true);
    const router = useRouter();

    useEffect(() => {
        onboardingApi.getStatus().then((res) => {
            if (!res.data) router.push("/onboarding");
        });

        fetchData();
    }, [router]);

    const fetchData = async () => {
        setIsLoading(true);
        try {
            const statusRes = await entryApi.getTodayStatus();
            setHasWrittenToday(statusRes.data);
            const entriesRes = await entryApi.getAll();
            setEntries(entriesRes.data.reverse()); // Newest first
        } catch (err) {
            console.error("Failed to fetch journal data", err);
        } finally {
            setIsLoading(false);
        }
    };

    const handleCreateEntry = async () => {
        if (!content.trim() || content.length > 300) return;
        try {
            await entryApi.create(content);
            setContent("");
            fetchData();
        } catch (err) {
            alert("Only one entry per day is allowed.");
        }
    };

    const handleReflect = async () => {
        try {
            const res = await intelligenceApi.getSummary();
            setSummary(res.data.summary);
        } catch (err) {
            setSummary("The void is temporarily silent.");
        }
    };

    if (isLoading) return <div className="min-h-screen bg-[#0a0a0a]" />;

    return (
        <div className="max-w-4xl mx-auto px-6 py-20 min-h-screen">
            {/* Header */}
            <header className="mb-20 flex justify-between items-end border-b border-border pb-6">
                <div>
                    <h1 className="text-3xl font-bold uppercase tracking-tighter">Journal</h1>
                    <p className="text-muted text-sm text-mono">Silence is the only architecture.</p>
                </div>
                <div className="text-right text-xs text-muted text-mono uppercase">
                    {new Date().toLocaleDateString('en-US', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })}
                </div>
            </header>

            {/* Daily Input Section */}
            <section className="mb-32">
                <AnimatePresence mode="wait">
                    {!hasWrittenToday ? (
                        <motion.div
                            key="input"
                            initial={{ opacity: 0 }}
                            animate={{ opacity: 1 }}
                            exit={{ opacity: 0 }}
                            className="space-y-6"
                        >
                            <textarea
                                value={content}
                                onChange={(e) => setContent(e.target.value)}
                                maxLength={300}
                                placeholder="Submit today's thought... (max 300)"
                                className="w-full bg-transparent border-none text-2xl font-light focus:outline-none placeholder:opacity-20 resize-none h-40"
                            />
                            <div className="flex justify-between items-center pt-4 border-t border-border border-dashed">
                                <span className={`text-xs text-mono ${content.length > 250 ? 'text-red-500' : 'text-muted'}`}>
                                    {content.length}/300
                                </span>
                                <button
                                    disabled={!content.trim()}
                                    onClick={handleCreateEntry}
                                    className="px-6 py-2 border border-white uppercase text-xs font-bold hover:bg-white hover:text-black transition-all disabled:opacity-20"
                                >
                                    Save Entry
                                </button>
                            </div>
                        </motion.div>
                    ) : (
                        <motion.div
                            key="locked"
                            initial={{ opacity: 0 }}
                            animate={{ opacity: 1 }}
                            exit={{ opacity: 0 }}
                            className="p-12 border border-border bg-[#0d0d0d] text-center space-y-4"
                        >
                            <LockIcon className="mx-auto w-6 h-6 text-muted" />
                            <p className="text-muted italic uppercase text-xs tracking-widest">Today is complete.</p>
                            <p className="text-sm">The silence has been recorded. Wait for tomorrow's observation.</p>
                        </motion.div>
                    )}
                </AnimatePresence>
            </section>

            {/* History Section */}
            <section className="space-y-24">
                {entries.map((entry) => (
                    <article key={entry.id} className="group relative">
                        <div className="flex gap-8">
                            <div className="w-1/4 pt-2">
                                <div className="text-xs text-mono text-muted uppercase sticky top-20">
                                    {new Date(entry.entryDate).toLocaleDateString('en-US', { month: 'short', day: '2-digit' })}
                                </div>
                            </div>
                            <div className="w-3/4 space-y-6">
                                <p className="text-xl leading-relaxed text-foreground opacity-90">
                                    {entry.content}
                                </p>
                                {entry.aiFeedback && (
                                    <div className="pl-6 border-l-2 border-muted">
                                        <p className="text-sm text-muted italic font-serif opacity-60 group-hover:opacity-100 transition-opacity duration-1000">
                                            — {entry.aiFeedback}
                                        </p>
                                    </div>
                                )}
                            </div>
                        </div>
                    </article>
                ))}
            </section>

            {/* Reflection Footer */}
            <div className="mt-40 border-t border-border pt-20 pb-40 text-center">
                {!summary ? (
                    <button
                        onClick={handleReflect}
                        className="group flex flex-col items-center gap-4 mx-auto"
                    >
                        <div className="w-12 h-12 rounded-full border border-muted flex items-center justify-center group-hover:border-white transition-colors">
                            <Sparkles className="w-4 h-4 text-muted group-hover:text-white transition-colors" />
                        </div>
                        <span className="text-xs text-muted text-mono uppercase tracking-widest group-hover:text-white transition-colors">Invite Reflection</span>
                    </button>
                ) : (
                    <motion.div
                        initial={{ opacity: 0 }}
                        animate={{ opacity: 1 }}
                        className="max-w-lg mx-auto space-y-6"
                    >
                        <h3 className="text-xs text-muted text-mono uppercase tracking-[0.3em]">The Observer reflects</h3>
                        <p className="text-lg italic font-light leading-relaxed">
                            "{summary}"
                        </p>
                        <button
                            onClick={() => setSummary(null)}
                            className="text-[10px] text-muted text-mono uppercase border-b border-muted hover:text-white hover:border-white transition-all"
                        >
                            Clear Thought
                        </button>
                    </motion.div>
                )}
            </div>
        </div>
    );
}

function LockIcon({ className }: { className?: string }) {
    return (
        <svg
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeWidth="2"
            strokeLinecap="round"
            strokeLinejoin="round"
            className={className}
        >
            <rect width="18" height="11" x="3" y="11" rx="2" ry="2" />
            <path d="M7 11V7a5 5 0 0 1 10 0v4" />
        </svg>
    );
}
