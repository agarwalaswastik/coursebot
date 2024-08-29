import Navbar from "./components/Navbar.tsx";
import {useState} from "react";
import SubmitArticle from "./feature/SubmitArticle.tsx";
import ViewArticles from "./feature/ViewArticles.tsx";
import CreateCourse from "./feature/CreateCourse.tsx";

type FeatureType = undefined | "Submit Article" | "View Articles" | "Create Course";

export default function App() {
    const [activeFeature, setActiveFeature] = useState<FeatureType>();

    const buttonStyles = "p-4 border-slate-700 border-2 rounded-md font-semibold text-2xl text-slate-300 bg-[#222222] hover:bg-emerald-700 transition-colors";
    const selectedStyles = {backgroundColor: "#059669"};

    const submitArticleStyles = activeFeature == "Submit Article" ? selectedStyles : {};
    const viewArticlesStyles = activeFeature == "View Articles" ? selectedStyles : {};
    const createCourseStyles = activeFeature == "Create Course" ? selectedStyles : {};

    return (
        <>
            <header><Navbar/></header>
            <main>
                <div className="flex justify-center">
                    <button className={buttonStyles} style={submitArticleStyles}
                            onClick={() => setActiveFeature("Submit Article")}>Submit Article
                    </button>
                    <button className={buttonStyles} style={viewArticlesStyles}
                            onClick={() => setActiveFeature("View Articles")}>View Articles
                    </button>
                    <button className={buttonStyles} style={createCourseStyles}
                            onClick={() => setActiveFeature("Create Course")}>Create Course
                    </button>
                </div>
                <div className="flex justify-center mt-12">
                    {activeFeature == "Submit Article" && <SubmitArticle/>}
                    {activeFeature == "View Articles" && <ViewArticles/>}
                    {activeFeature == "Create Course" && <CreateCourse/>}
                </div>
            </main>
        </>
    );
}
