"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";
import { onboardingApi } from "@/lib/api";

export default function Home() {
  const router = useRouter();

  useEffect(() => {
    onboardingApi.getStatus().then((res) => {
      if (res.data) {
        router.push("/journal");
      } else {
        router.push("/onboarding");
      }
    });
  }, [router]);

  return (
    <div className="flex items-center justify-center min-h-screen bg-[#0a0a0a]">
      <div className="animate-pulse text-muted text-mono uppercase tracking-widest text-xs">
        Connecting to the void...
      </div>
    </div>
  );
}
